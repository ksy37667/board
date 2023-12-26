package com.board.rest.dto.request

import com.board.entity.enums.SocialType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SignupRequest(

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
        message = "이메일 형식을 맞춰야합니다")
    val email: String,

    @NotBlank
    @Size(min = 3, max = 20)
    val username: String,

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{7,16}$",
        message = "비밀번호는 영문+숫자+특수문자를 포함한 8~20자여야 합니다")
    val password: String,

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z가-힣\\\\s]{2,15}",
        message = "이름은 영문자, 한글, 공백 포함 2글자부터 15글자까지 가능합니다.")
    val nickName: String,

    @NotBlank
    val socialType: SocialType,
)