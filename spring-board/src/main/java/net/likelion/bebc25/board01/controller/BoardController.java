package net.likelion.bebc25.board01.controller;

import lombok.extern.slf4j.Slf4j;
import net.likelion.bebc25.board01.dto.PostDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class BoardController {

    private List<PostDto> fakePosts;

    public BoardController() {
        fakePosts = new ArrayList<PostDto>();
        PostDto post1 = new PostDto();
        post1.setId(1);
        post1.setTitle("Post 1");
        post1.setContent("This is a post 1");
        post1.setAuthor("Haru");
        post1.setCreatedAt(LocalDateTime.now());

        PostDto post2 = new PostDto();
        post2.setId(2);
        post2.setTitle("Post 2");
        post2.setContent("This is a post 2");
        post2.setAuthor("Haru");
        post2.setCreatedAt(LocalDateTime.now());

        PostDto post3 = new PostDto();
        post3.setId(3);
        post3.setTitle("Post 3");
        post3.setContent("This is a post 3");
        post3.setAuthor("Haru");
        post3.setCreatedAt(LocalDateTime.now());

        fakePosts.add(post1);
        fakePosts.add(post2);
        fakePosts.add(post3);
    }

    public List<PostDto> getPosts() {
        return fakePosts;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public String getIndex() {
        String result = """
                <!DOCTYPE html>
                <html lang="ko">
                <head>
                  <meta charset="UTF-8">
                  <title>스프링 부트 게시판 데모 홈</title>
                  <link rel="stylesheet" href="/board/css/common.css">
                  <link rel="stylesheet" href="/board/css/index.css">
                </head>
                <body>
                  <div class="container">
                    <div class="welcome-card">
                      <h1>스프링 부트 게시판 v.01</h1>
                      <p>스프링 부트 게시판에 오신걸 환영합니다.</p>
                      <p><a href="/01/board/list.html">01 - Controller에서 HTML 하드코딩</a>
                      <p>현재 시간은 %s 입니다.</p>
                
                      <div>
                        <a href="board/list.html" class="btn-lg">게시글 목록으로 이동</a>
                      </div>
                    </div>
                  </div>
                </body>
                </html>
                """;
        result = result.formatted(LocalDateTime.now());

        return result;
    }


    @GetMapping("/01/board/list.html")
    @ResponseBody
    public String getBoardList() {
        // 게시글 목록 조회(데이터)
        List<PostDto> posts = getPosts();

        // View
        String result = """
                <!DOCTYPE html>
                <html lang="ko">
                <head>
                  <meta charset="UTF-8">
                  <title>스프링 게시판 - 목록</title>
                  <link rel="stylesheet" href="/board/css/common.css">
                  <link rel="stylesheet" href="/board/css/list.css">
                </head>
                <body>
                  <div class="container">
                    <h1>게시글 목록</h1>
                    <div class="nav">
                      <a href="list.html">목록으로</a>
                      <a href="write.html">새 글 쓰기</a>
                    </div>
                
                    <table>
                      <thead>
                        <tr>
                          <th>번호</th>
                          <th>제목</th>
                          <th>작성자</th>
                          <th>작성일시</th>
                          <th>작업</th>
                        </tr>
                      </thead>
                      <tbody>
                """;

        for (PostDto post : posts) {
            result += """
                    <tr>
                      <td>%s</td>
                      <td>
                        <a href="detail.html?id=%s">%s</a>
                      </td>
                      <td>%s</td>
                      <td>%s</td>
                      <td>
                        <a href="list.html" class="btn btn-danger">삭제</a>
                      </td>
                    </tr>
                    """.formatted(
                    post.getId(),
                    post.getId(),
                    post.getTitle(),
                    post.getAuthor(),
                    post.getCreatedAt()
            );
        }


        result += """   
                      </tbody>
                    </table>
                  </div>
                </body>
                </html>
                
                """;

        return result;
    }

    @GetMapping("/01/board/detail.html")
    @ResponseBody
    public String getDetail(@RequestParam("id") int id) {
        List<PostDto> posts = getPosts();
        PostDto post = posts.get(id-1);
        String result = """
                <!DOCTYPE html>
                <html lang="ko">
                <head>
                  <meta charset="UTF-8">
                  <title>스프링 게시판 - 상세 보기</title>
                  <link rel="stylesheet" href="/board/css/common.css">
                  <link rel="stylesheet" href="/board/css/detail.css">
                </head>
                <body>
                  <div class="container">
                    <h1>게시글 상세 정보</h1>
                    <div class="nav">
                      <a href="list.html">목록으로</a>
                      <a href="write.html">새 글 쓰기</a>
                    </div>
                """;
        result += """
                <table style="margin-bottom: 20px;">
                  <tr>
                    <th style="width: 60px;">번호</th>
                    <td>%s</td>
                  </tr>
                  <tr>
                    <th>제목</th>
                    <td>%s</td>
                  </tr>
                  <tr>
                    <th>작성자</th>
                    <td>%s</td>
                  </tr>
                  <tr>
                    <th>작성일시</th>
                    <td>%s</td>
                  </tr>
                  <tr>
                    <th>내용</th>
                    <td style="white-space: pre-wrap;">%s</td>
                  </tr>
                </table>
                """.formatted(post.getId(), post.getTitle(), post.getAuthor(), post.getContent(), post.getCreatedAt());
        ;
        result += """
                            <div>
                              <a href="edit?id=%s" class="btn">수정하기</a>
                              <a href="list.html" class="btn btn-secondary">목록으로</a>
                            </div>
                          </div>
                        </body>
                        </html>
                """.formatted(post.getId());

        return result;
    }

    @GetMapping("/01/board/write.html")
    @ResponseBody
    public String write() {
        String result = """
                    <!DOCTYPE html>
                    <html lang="ko">
                    <head>
                      <meta charset="UTF-8">
                      <title>스프링 게시판 - 새 글 쓰기</title>
                      <link rel="stylesheet" href="/board/css/common.css">
                      <link rel="stylesheet" href="/board/css/write.css">
                    </head>
                    <body>
                      <div class="container">
                        <h1>게시글 등록</h1>
                        <div class="nav">
                          <a href="list.html">목록으로</a>
                          <a href="write.html">새 글 쓰기</a>
                        </div>
                        <form action="/01/board/new" METHOD="POST">
                          <div class="form-group">
                            <label for="title">제목</label>
                            <input type="text" id="title" name="title" placeholder="제목을 입력하세요" required>
                          </div>
                          <div class="form-group">
                            <label for="author">작성자</label>
                            <input type="text" id="author" name="author" placeholder="작성자 이름을 입력하세요" required>
                          </div>
                          <div class="form-group">
                            <label for="content">내용</label>
                            <textarea id="content" name="content" rows="10" placeholder="내용을 입력하세요" required></textarea>
                          </div>
                          <div style="margin-top: 20px;">
                            <button type="submit" class="btn">등록</button>
                            <a href="list.html" class="btn btn-secondary">취소</a>
                          </div>
                        </form>
                      </div>
                    </body>
                    </html>
                """;

        return result;
    }

    @PostMapping("/01/board/new")
    @ResponseBody
    public String writePost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("author") String author
    ) {
        PostDto postDto = new PostDto(title, content, author);

        int nextId;

        if (fakePosts.isEmpty()) {
            nextId = 1;
        } else {
            nextId = fakePosts.get(fakePosts.size() - 1).getId();
        }

        postDto.setId(nextId);
        postDto.setCreatedAt(LocalDateTime.now());

        fakePosts.add(postDto);

        log.debug("등록 요청 DTD: {}", postDto);
        return "등록 완료";
    }

    public List<PostDto> savePost(PostDto postDto) {
        postDto.setId(fakePosts.get(fakePosts.size() - 1).getId() + 1);
        this.fakePosts.add(postDto);
        return this.fakePosts;
    }

    @GetMapping("/01/board/edit")
    @ResponseBody
    public String getEditForm(@RequestParam int id) {
        PostDto postDto = fakePosts.get(id-1);
        String result = """
                    <!DOCTYPE html>
                    <html lang="ko">
                    <head>
                      <meta charset="UTF-8">
                      <title>스프링 게시판 - 글 수정하기</title>
                      <link rel="stylesheet" href="/board/css/common.css">
                      <link rel="stylesheet" href="/board/css/write.css">
                    </head>
                    <body>
                      <div class="container">
                        <h1>게시글 수정</h1>
                        <div class="nav">
                          <a href="list.html">목록으로</a>
                          <a href="write.html">새 글 쓰기</a>
                        </div>
                
                        <form action="edit" METHOD="POST">
                          <input type="hidden" id="id" value="%s">
                
                          <div class="form-group">
                            <label for="title">제목</label>
                            <input type="text" id="title" name="title" value="%s" required>
                          </div>
                
                          <div class="form-group">
                            <label for="author">작성자</label>
                            <input type="text" id="author" name="author" value="%s" required>
                          </div>
                
                          <div class="form-group">
                            <label for="content">내용</label>
                            <textarea id="content" name="content" rows="10" required>%s</textarea>
                          </div>
                
                          <div style="margin-top: 20px;">
                            <button type="submit" class="btn">수정</button>
                            <a href="detail.html" class="btn btn-secondary">취소</a>
                          </div>
                        </form>
                      </div>
                    </body>
                    </html>
                """.formatted(postDto.getId(), postDto.getTitle(), postDto.getContent(), postDto.getAuthor());

        return result;
    }

    @PostMapping("/01/board/edit")
    public String editPost(@ModelAttribute PostDto postDto) {
        log.debug(postDto.toString());
        updatePost(postDto);
        return "redirect:detail.html";
    }

    public void updatePost(PostDto postDto) {
        PostDto targetPost = null;
        for (PostDto post : getPosts()) {
            if (post.getId() == targetPost.getId()) {
                targetPost = post;
                break;
            }
        }
        targetPost.setTitle(postDto.getTitle());
        targetPost.setContent(postDto.getContent());
        targetPost.setAuthor(postDto.getAuthor());
    }


    @PostMapping("/01/board/delete")
    public String deletePost() {
        return "삭제 완료 후 보여줄 페이지";
    }
}