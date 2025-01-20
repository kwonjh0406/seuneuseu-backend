package kwonjh0406.sns.post.service;

import jakarta.persistence.EntityNotFoundException;
import kwonjh0406.sns.aws.s3.service.S3Service;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.post.dto.PostContentDto;
import kwonjh0406.sns.post.dto.CreatePostRequest;
import kwonjh0406.sns.post.dto.PostResponse;
import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.post.entity.PostImage;
import kwonjh0406.sns.post.repository.PostImageRepository;
import kwonjh0406.sns.post.repository.PostRepository;
import kwonjh0406.sns.user.entity.User;
import kwonjh0406.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final PostImageRepository postImageRepository;

    public void createPost(CreatePostRequest createPostRequest) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("인증되지 않은 사용자입니다.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomOAuth2User oAuth2User) {
            Post post = Post.builder()
                    .content(createPostRequest.getContent())
                    .user(oAuth2User.getUser())
                    .likes(0L)
                    .replies(0L)
                    .build();
            postRepository.save(post);
            if (createPostRequest.getImages() != null) {
                for (MultipartFile imageFile : createPostRequest.getImages()) {
                    String imageUrl = s3Service.uploadImageToS3(imageFile);
                    PostImage postImage = PostImage.builder()
                            .imageUrl(imageUrl)
                            .post(post)
                            .build();
                    postImageRepository.save(postImage);
                }
            }
        }
    }

    public List<PostResponse> getPostsByUsername(String username) {

        User user = userRepository.findByUsername(username);

        List<Post> posts = postRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "id"));
        List<PostResponse> postResponses = new ArrayList<>();

        for (Post post : posts) {
            List<PostImage> postImages = postImageRepository.findByPost(post);

            List<String> imageUrls = postImages.stream()
                    .map(PostImage::getImageUrl)
                    .collect(Collectors.toList());

            postResponses.add(new PostResponse(post, imageUrls));
        }

        return postResponses;
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<PostResponse> postResponses = new ArrayList<>();

        for (Post post : posts) {
            List<PostImage> postImages = postImageRepository.findByPost(post);

            List<String> imageUrls = postImages.stream()
                    .map(PostImage::getImageUrl)
                    .collect(Collectors.toList());

            postResponses.add(new PostResponse(post, imageUrls));
        }

        return postResponses;
    }

    public PostResponse getPostByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("포스트를 찾을 수 없습니다.")
        );
        List<PostImage> postImages = postImageRepository.findByPost(post);
        List<String> imageUrls = postImages.stream()
                .map(PostImage::getImageUrl)
                .collect(Collectors.toList());
        return new PostResponse(post, imageUrls);
    }

    public void deletePostByPostId(Long postId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // 삭제할 게시글을 가져옴
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new EntityNotFoundException("포스트를 찾을 수 없거나 이미 삭제되었습니다.")
            );

            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomOAuth2User oAuth2User) {
                // 로그인 한 사용자와 게시글 작성자가 동일한지 확인
                if (oAuth2User.getUser().getUsername().equals(post.getUser().getUsername())) {
                    // 게시글의 이미지들을 먼저 S3에서 삭제해줌
                    List<String> imageUrls = postImageRepository.findAllImageUrlsByPostId(postId);
                    for (String imageUrl : imageUrls) {
                        s3Service.deleteImageFromS3(imageUrl);
                    }
                    // 이후 게시글을 삭제 (게시글 이미지들은 Cascade 제약 걸려있음)
                    postRepository.delete(post);
                    return;
                } else {
                    throw new AccessDeniedException("본인의 게시글만 삭제할 수 있습니다.");
                }
            }
        }
        throw new AccessDeniedException("로그인이 필요합니다.");
    }


    public PostContentDto getPostEditByPostId(Long postId) throws Exception {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("Post with ID " + postId + " not found")
        );

        List<PostImage> postImages = postImageRepository.findByPost(post);

        List<String> imageUrls = postImages.stream()
                .map(PostImage::getImageUrl)
                .collect(Collectors.toList());

        return PostContentDto.builder()
                .content(post.getContent())
                .images(imageUrls)
                .build();
    }

    public void editPostByPostId(Long postId, String content) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setContent(content);
        postRepository.save(post);
    }
}
