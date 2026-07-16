package net.likelion.bebc25.board3.repository;

import net.likelion.bebc25.board3.post.PostDto;

import java.util.List;

public interface PostRepository {

    public List<PostDto> findAll();
    public PostDto findById(long id);
    public void save(PostDto postDto);
    public void update(PostDto postDto);
    public void deleteById(int id);
}
