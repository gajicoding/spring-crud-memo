package com.example.memo.repository;

import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoRepositoryImple implements MemoRepository{
    private final Map<Long, Memo> memoList = new HashMap<>();


    @Override
    public Memo saveMemo(Memo memo) {
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;
        memo.setId(memoId);

        memoList.put(memoId, memo);

        return memo;
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        List<MemoResponseDto> allMemos = new ArrayList<>();

        for (Memo memo : memoList.values()) {
            MemoResponseDto responseDto = new MemoResponseDto(memo);
            allMemos.add(responseDto);
        }

        return allMemos;
    }

    @Override
    public Memo findMemoById(Long id) {
        return memoList.get(id);
    }

    @Override
    public void updateMemo(Long id, Memo memo) {
        memoList.put(id, memo);
    }

    @Override
    public void deleteMemo(Long id) {
        memoList.remove(id);
    }
}
