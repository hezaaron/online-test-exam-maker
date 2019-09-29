package com.iplusplus.coding.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.iplusplus.coding.entity.Answer;
import com.iplusplus.coding.entity.Protocol;
import com.iplusplus.coding.event.EventDispatcher;
import com.iplusplus.coding.event.CodingTakenEvent;
import com.iplusplus.coding.model.Grade;
import com.iplusplus.coding.model.Mark;
import com.iplusplus.coding.repository.AnswerRepository;
import com.iplusplus.coding.repository.ProtocolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

	private final ProtocolRepository protocolRepository;
	private final AnswerRepository answerRepository;
    private final EventDispatcher eventDispatcher;
	
	@Override
	@Transactional
    public Protocol updateExamProtocol(Protocol protocol) {
    	boolean isPass = protocol.getGrade() >= Mark.PASS_MARK;
    	eventDispatcher.send(new CodingTakenEvent(protocol.getId(), protocol.getUser(), isPass));
        return protocolRepository.save(protocol);
    }

	@Override
	public void computeGrade(Protocol protocol, Integer examId, List<Long> userAnswers, String user) {
		final List<Answer> correctAnswers = answerRepository.findByQuestionQuizIdAndCorrect(examId, true);
        final List<Long> correctAnswerIds = correctAnswers.stream().map(Answer::getId)
	        														  .collect(Collectors.toList());
        new Grade(protocol, correctAnswerIds, userAnswers, user).computeGrade();
	}

	@Override
	public Map<String, Object> getQuizStats(Long protocolId) {
		final Protocol protocol = getExamProtocol(protocolId);
        final Map<String, Object> map = new HashMap<>();
        map.put("title", String.format("Your result for %s", protocol.getQuiz().getName()));
        map.put("startTime", getTimeFormat(protocol.getStartTime().toLocalTime()));
        map.put("finishTime", getTimeFormat(protocol.getFinishTime().toLocalTime()));
        map.put("questionCount", protocol.getQuestionCount());
        map.put("grade", protocol.getGrade());
        map.put("maxGrade", Mark.MAX_MARK);
        return map;
	}

	@Override
	public Protocol getExamProtocol(Long protocolId) {
		return protocolRepository.getOne(protocolId);
	}

	private String getTimeFormat(LocalTime time) {
    	return DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US).format(time);
    }
}