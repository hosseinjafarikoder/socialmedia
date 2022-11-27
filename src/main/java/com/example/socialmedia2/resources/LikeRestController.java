package com.example.socialmedia2.resources;

import com.example.socialmedia2.dto.LikeDto;
import com.example.socialmedia2.entity.AccountEntity;

import com.example.socialmedia2.entity.LikeEntity;
import com.example.socialmedia2.entity.PostEntity;
import com.example.socialmedia2.exceptions.BaseException;
import com.example.socialmedia2.mapper.LikeMapper;
import com.example.socialmedia2.service.AccountService;

import com.example.socialmedia2.service.LikeService;
import com.example.socialmedia2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeRestController {
    private final LikeService likeService;
    private final LikeMapper likeMapper;
    private final PostService postService;
    private final AccountService accountService;

    @PostMapping
    public LikeDto createLike(Principal principal, @RequestBody LikeDto likeDto) throws BaseException {
        AccountEntity accountEntity = accountService.findByUsername(principal.getName());
        LikeEntity likeEntity = likeMapper.convertDtoToT(likeDto);
        PostEntity post = postService.findById(likeDto.getPostId());
        AccountEntity account = accountService.findById(accountEntity.getId());
        likeEntity.setPost(post);
        likeEntity.setAccount(account);
        return likeMapper.convertTToDto(likeService.saveOrUpdate(likeEntity));
    }


    @DeleteMapping("/{id}")
    public void deleteLikeById(@PathVariable Long id) throws BaseException {
        likeService.deleteById(id);
    }
}
