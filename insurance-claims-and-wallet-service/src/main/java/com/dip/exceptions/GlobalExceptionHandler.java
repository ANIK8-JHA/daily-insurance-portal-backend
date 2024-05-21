package com.dip.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GlobalExceptionHandler {
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> argumentErrorHandler(MethodArgumentNotValidException ex) {
		Map<String, String> map = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			map.put(error.getField(), error.getDefaultMessage());
		});
		return map;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WalletNotFoundException.class)
	public Map<String, String> walletNotFoundExceptionHandler(WalletNotFoundException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("ErrorMessage", ex.getMessage());
		return map;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotEnoughBalanceException.class)
	public Map<String, String> NotEnoughBalanceHandler(NotEnoughBalanceException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("ErrorMessage", ex.getMessage());
		return map;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AmountExceededLimitException.class)
	public Map<String, String> AmountExceededLimitHandler(AmountExceededLimitException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("ErrorMessage", ex.getMessage());
		return map;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ClaimAmountGreaterThanCoverageAmountException.class)
	public Map<String, String> ClaimAmountGreaterThanCoverageAmountHandler(ClaimAmountGreaterThanCoverageAmountException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("ErrorMessage", ex.getMessage());
		return map;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PolicyAlreadyClaimedException.class)
	public Map<String, String> PolicyAlreadyClaimedHandler(PolicyAlreadyClaimedException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("ErrorMessage", ex.getMessage());
		return map;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BuyingPolicyOnTheSameDayException.class)
	public Map<String, String> BuyingPolicyOnTheSameDayHandler(BuyingPolicyOnTheSameDayException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("ErrorMessage", ex.getMessage());
		return map;
	}
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SubmittingClaimOnTheSameDayException.class)
	public Map<String, String> SubmittingClaimOnTheSameDayHandler(SubmittingClaimOnTheSameDayException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("ErrorMessage", ex.getMessage());
		return map;
	}
	
//	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(SubmittingClaimOnTheSameDayException.class)
//	public Map<String, String> SubmittingClaimOnTheSameDayHandler(SubmittingClaimOnTheSameDayException ex) {
//		Map<String, String> map = new HashMap<>();
//		map.put("ErrorMessage", ex.getMessage());
//		return map;
//	}
}
