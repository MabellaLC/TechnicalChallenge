package unitTest;

import clickSystem.domain.valueObjects.InitialBudget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitialBudgetShould {
    @Test
    public void calculate_five_percent_of_a_total_budget(){
        InitialBudget initialBudget = new InitialBudget(1.00);

        assertEquals(0.05, initialBudget.calculateFivePercentOfInitialBudget());
    }
}
