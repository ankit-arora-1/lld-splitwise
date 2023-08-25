package com.scaler.splitwise.services;

import com.scaler.splitwise.models.Expense;
import com.scaler.splitwise.models.ExpenseUser;
import com.scaler.splitwise.models.Group;
import com.scaler.splitwise.models.User;
import com.scaler.splitwise.repositories.ExpenseRepository;
import com.scaler.splitwise.repositories.ExpenseUserRepository;
import com.scaler.splitwise.repositories.GroupRepository;
import com.scaler.splitwise.repositories.UserRepository;
import com.scaler.splitwise.strategies.SettleUpStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private GroupRepository groupRepository;
    private ExpenseRepository expenseRepository;
    private SettleUpStrategy settleUpStrategy;
    private UserRepository userRepository;
    private ExpenseUserRepository expenseUserRepository;

    public List<Expense> settleUpUser(Long userId) {
        /*
        1. Get all the expenses which the user is part of
        2. Iterate over all of the expenses and find out of all people
             involved in those expenses
        3. Use min and max heap to find all the transactions (SettleUp strategy)
        4. Return the transaction
         */

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new RuntimeException();
        }

        User user = userOptional.get();

        List<ExpenseUser> expenseUsers =
                expenseUserRepository.findAllByUser(user);
        // Expense(B paid 1000, A had to pay 500, B had to pay 500)
        // Expense(C paid 1000, A had to pay 500, C had to pay 500)
        // Expense(A paid 1000, D had to pay 500, A had to pay 500)

        List<Expense> expenses = new ArrayList<>();
        for(ExpenseUser expenseUser: expenseUsers) { // hw: find a better way to do this
            expenses.add(expenseUser.getExpense());
        }

        List<Expense> settledUpTransactions =
                settleUpStrategy.settleUp(expenses);
        // A -> B
        // D -> B
        // C -> D
        // C -> A


        // Filter only that users transactions
        List<Expense> expenseToReturn = new ArrayList<>();
        for(Expense expense: settledUpTransactions) {
            for(ExpenseUser expenseUser: expenseUsers) {
                if(expenseUser.getUser().equals(user) &&
                expenseUser.getExpense().equals(expense)) {
                    expenseToReturn.add(expense);
                    break;
                }
            }
        }

//        for(Expense expense: settledUpTransactions) {
//            for(ExpenseUser expenseUser: expense.getExpenseUsers()) {
//                if(expenseUser.getUser().equals(user)) {
//                    expenseToReturn.add(expense);
//                    break;
//                }
//            }
//        }

        return expenseToReturn;
    }

    public List<Expense> settleUpGroup(Long groupId) {
        /*
        1. Get all the expenses of the group
        2. Iterate over all of the expenses and find out of all people
             involved in those expenses
        3. Use min and max heap to find all the transactions (SettleUp strategy)
        4. Return all the transactions
         */
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty()) {
            throw new RuntimeException();
        }

        Group group = groupOptional.get();

        List<Expense> expenses = expenseRepository.findAllByGroup(group);

        List<Expense> expensesToSettleUp = settleUpStrategy.settleUp(expenses);

        return expensesToSettleUp;
    }
}
