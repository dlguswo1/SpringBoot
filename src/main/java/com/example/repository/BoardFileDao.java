package com.example.repository;

import com.example.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardFileDao extends JpaRepository<BoardFile, Integer> {

    List<BoardFile> findAllByBoardId(Integer id);
}
