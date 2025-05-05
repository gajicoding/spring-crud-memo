package com.example.memo.repository;

import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;

import java.util.List;

public interface MemoRepository {
    Memo saveMemo(Memo memo);

    List<MemoResponseDto> findAllMemos();

    Memo findMemoById(Long id);

    void updateMemo(Long id, Memo memo);

    void deleteMemo(Long id);
}
