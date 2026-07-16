package net.likelion.bebc25.board3.post;

import lombok.extern.slf4j.Slf4j;
import net.likelion.bebc25.board3.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/03/board")
public class BoardController {

    private final PostService postService;

    public BoardController(PostService postService) {
        this.postService = postService;
    }

    public List<PostDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/list.html")
    public String getBoardList(Model model) {
        // 게시글 목록 조회(데이터)
        model.addAttribute("posts", getPosts());

        return "board/list";
    }

    @GetMapping("/detail.html")
    public String getDetail(@RequestParam("id") int id, Model model) {
        model.addAttribute("post", postService.getPost(id));
        return "board/detail";
    }

    @GetMapping("/write.html")
    public String writeForm(Model model) {
        model.addAttribute("postForm", new PostDto());
        return "board/write";
    }

    @PostMapping("/write")
    public String writePost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("author") String author
    ) {
        PostDto post = new PostDto();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());
        post.setSecret(false);
        postService.writePost(post);
        log.debug(post.toString());
        return "redirect:detail.html?id=" + post.getId();
    }

    @GetMapping("/edit.html")
    public String getEditForm(@RequestParam("id") int id, Model model) {
        PostDto post = getPosts().get(id-1);

        model.addAttribute("postForm", post);
        return "board/write";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("postForm") PostDto postDto) {
        log.debug(postDto.toString());
        postService.updatePost(postDto);
        return "redirect:detail.html?id=" + postDto.getId();
    }


    @PostMapping("/delete")
    public String deletePost() {
        return "삭제 완료 후 보여줄 페이지";
    }
}