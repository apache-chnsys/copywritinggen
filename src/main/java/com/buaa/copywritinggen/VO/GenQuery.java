package com.buaa.copywritinggen.VO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GenQuery {

    private String genType;

    private String userText;

    private String flag;

    private String image;

    private boolean check;

    private String text;
}
