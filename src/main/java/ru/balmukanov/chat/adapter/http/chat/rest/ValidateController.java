package ru.balmukanov.chat.adapter.http.chat.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.balmukanov.chat.adapter.http.chat.dto.ValidateResponseDto;
import ru.balmukanov.chat.adapter.http.chat.dto.ValidateUsernameRequestDto;

@RestController
@RequestMapping("/validate")
public class ValidateController {

	@PostMapping("/username")
	public ValidateResponseDto validateUsername(@RequestBody ValidateUsernameRequestDto request) {
		if (request.getUsername().equals("ddddd")) {
			return new ValidateResponseDto(false, "Username taken");
		}
		return new ValidateResponseDto(true, null);
	}
}