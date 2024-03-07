package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.LoginHistory;
import com.example.model.LoginHistoryDto;
import com.example.repository.HistoryDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryService {

    private final HistoryDao historyDao;

    /*
     * ver1 public void saveLogOnLogin(LoginHistory loginHistory) {
     * historyDao.save(loginHistory); }
     */
	/*
	//ver2
	public void saveLogOnLogin(String activitytype, String memberId) {

		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setMemberId(memberId);
		if ("logout".equals(activitytype))
			loginHistory.setExpiredDate(new Date());
		historyDao.save(loginHistory);

	}
	*/
    // ver3 확정
    @Transactional
    public void saveLogOnLogin(String activitytype, String memberId) {
        if("login".equals(activitytype)) {
            LoginHistory loginHistory = new LoginHistory();
            loginHistory.setMemberId(memberId);
            historyDao.save(loginHistory);
        }
        else if("logout".equals(activitytype)){
            LoginHistory lastLoginHistory = historyDao.findTopByMemberIdOrderByCreateDateDesc(memberId);

            if(lastLoginHistory != null) {
                lastLoginHistory.setExpiredDate(new Date());
                historyDao.save(lastLoginHistory);
            }

        }
    }

//    public List<LoginHistoryDto> getLoginHistoryDto(String memberId) {
//        List<LoginHistory> loginHistories = historyDao.findLoginHistoriesByMemberIdOrderByCreateDateDesc(memberId);
//
//        return loginHistories.stream().map(LoginHistoryDto::fromEntity).collect(Collectors.toList());
//    }
}
