package net.likelion.bebc25.board02.controller;

import lombok.extern.slf4j.Slf4j;
import net.likelion.bebc25.board02.dto.PostDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/02/board")
public class BoardController {

    private List<PostDto> fakePosts;

    public BoardController() {
        fakePosts = new ArrayList<PostDto>();
        PostDto post1 = new PostDto();
        post1.setId(1);
        post1.setTitle("Title 1");
        post1.setContent("This is a post 1");
        post1.setAuthor("Haru");
        post1.setCreatedAt(LocalDateTime.now());

        PostDto post2 = new PostDto();
        post2.setId(2);
        post2.setTitle("Title 2");
        post2.setContent("This is a post 2");
        post2.setAuthor("Namu");
        post2.setCreatedAt(LocalDateTime.now());

        PostDto post3 = new PostDto();
        post3.setId(3);
        post3.setTitle("Title 3");
        post3.setContent("This is a post 3");
        post3.setAuthor("Minsu");
        post3.setCreatedAt(LocalDateTime.now());

        fakePosts.add(post1);
        fakePosts.add(post2);
        fakePosts.add(post3);
    }

    public List<PostDto> getPosts() {
        return fakePosts;
    }

    @GetMapping("/list.html")
    public String getBoardList(Model model) {
        // 게시글 목록 조회(데이터)
        model.addAttribute("posts", getPosts());

        return "board/list";
    }

    @GetMapping("/detail.html")
    public String getDetail(@RequestParam("id") int id, Model model) {
        PostDto post = getPosts().get(id-1);
        model.addAttribute("post", post);


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
        PostDto post = new PostDto(title, content, author);
        log.debug(post.toString());

        savePost(post);
        return "redirect:detail.html?id=" + post.getId();
    }

    public void savePost(PostDto postDto) {
        postDto.setId(fakePosts.get(fakePosts.size() - 1).getId() + 1);
        postDto.setCreatedAt(LocalDateTime.now());
        this.fakePosts.add(postDto);
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
        updatePost(postDto);
        return "redirect:detail.html?id=" + postDto.getId();
    }

    public void updatePost(PostDto postDto) {
        PostDto targetPost = null;
        for (PostDto org : getPosts()) {
            if (org.getId() == postDto.getId()) {
                targetPost = org;
                break;
            }
        }
        targetPost.setTitle(postDto.getTitle());
        targetPost.setContent(postDto.getContent());
        targetPost.setAuthor(postDto.getAuthor());
    }


    @PostMapping("/delete")
    public String deletePost() {
        return "삭제 완료 후 보여줄 페이지";
    }
}