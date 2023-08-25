package com.scaler.splitwise.commands;

import com.scaler.splitwise.controllers.ExpenseController;
import com.scaler.splitwise.dtos.SettleUpUserRequestDto;
import com.scaler.splitwise.dtos.SettleUpUserResponseDto;

import java.util.List;

public class SettleUpUser implements Command {
    private ExpenseController expenseController;
    @Override // "register" "username" 'password" -> Should not process this
    public boolean matches(String inp) {
        List<String> words = List.of(inp.split(" "));
        return words.size() == 2 &&
                words.get(0).equalsIgnoreCase(CommandKeywords.SettleUpCommand);
    }

    @Override
    public void execute(String inp) {
        List<String> words = List.of(inp.split(" "));
        Long userId = Long.valueOf(words.get(1));

        SettleUpUserRequestDto settleUpUserRequestDto =
                new SettleUpUserRequestDto();
        settleUpUserRequestDto.setUserId(userId);

        SettleUpUserResponseDto response =
                expenseController.settleUpUser(settleUpUserRequestDto);
    }
}
