package net.springboard.post.controller;

import net.springboard.post.dto.PostDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {

    private final List<PostDto> fakePosts;

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
    }

    public List<PostDto> getPosts() {
        List<PostDto> posts = new ArrayList<>();
        return posts;
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
    public String getBoardList(){
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

        for(PostDto post : posts){
            result += """
                    <tr>
                      <td>%s</td>
                      <td>
                        <a href="detail.html">%s</a>
                      </td>
                      <td>%s</td>
                      <td>%s</td>
                      <td>
                        <a href="list.html" class="btn btn-danger">삭제</a>
                      </td>
                    </tr>
                    """.formatted(
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
    public String getDetail(){
        PostDto post = getPosts().get(0);
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
                        <th style="width: 20%;">번호</th>
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
                    """;
        result += """
                    <div>
                      <a href="edit.html" class="btn">수정하기</a>
                      <a href="list.html" class="btn btn-secondary">목록으로</a>
                    </div>
                  </div>
                </body>
                </html>
        """.formatted(post.getId(), post.getTitle(), post.getAuthor(), post.getCreatedAt());

        return result;
    }

    @GetMapping("/01/board/write.html")
    @ResponseBody
    public String write(){
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
                <form action="list.html">
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
}
