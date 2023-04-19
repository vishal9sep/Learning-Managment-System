package com.masai.team4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TypeDto {

    private Integer id;
    @NotBlank
    @Size(min = 2, message = "Min size of type title is 2")
    private String type;
}
