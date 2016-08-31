package info.longshore.sudoku.domain;

import java.util.Set;
import java.util.TreeSet;

public class Ruleset
{
    public Ruleset()
    {
        rules = new Rule[0];
    }

    public Ruleset(Ruleset r)
    {
        rules = new Rule[r.rules.length];

        for (int i = 0; i < rules.length; i++) {
            try {
                rules[i] = r.rules[i].getClass().newInstance();
            } catch (Exception e) {
                // Ignore...shouldn't happen unless person compiling makes a rule in a dumb way.
            }
        }

    }

    public Ruleset(Rule[] r)
    {
        rules = r;
    }

    public Rule[] getRules()
    {
        return rules;
    }

    public Rule getRule(int num)
    {
        return rules[num];
    }

    public void addRule(Rule r)
    {
        Rule[] newRules = new Rule[rules.length + 1];

        for (int i = 0; i < rules.length; i++) {
            newRules[i] = rules[i];
        }

        newRules[rules.length] = r;

        rules = newRules;
    }

    public Set<Integer> getRelatedElementNumbers(ConstraintPuzzle cp, int elementNumber)
    {
        // Union of all the rules
        if (rules.length < 1) {
            return new TreeSet<Integer>();
        } else {
            Set<Integer> relatedNumbers = rules[0].getRelatedElementNumbers(cp, elementNumber);

            for (int i = 1; i < rules.length; i++) {
                Set<Integer> relatedNumbersForThisRule = rules[i].getRelatedElementNumbers(cp, elementNumber);

                relatedNumbers.addAll(relatedNumbersForThisRule);
            }

            return relatedNumbers;
        }
    }

    public Set<Integer> getValidElementValues(ConstraintPuzzle cp, int elementNumber)
    {
        if (rules.length < 1) {
            return new TreeSet<Integer>();
        } else {
            Set<Integer> validElements = new TreeSet<Integer>();
            for (int validElement : rules[0].getValidElementValues(cp, elementNumber)) {
                validElements.add(validElement);
            }

            for (int i = 1; i < rules.length; i++) {
                Set<Integer> validElementsForThisRule = new TreeSet<Integer>();
                for (int validElement : rules[i].getValidElementValues(cp, elementNumber)) {
                    validElementsForThisRule.add(validElement);
                }

                validElements.retainAll(validElementsForThisRule);
            }

            return validElements;
        }
    }

    private Rule[] rules;
}
