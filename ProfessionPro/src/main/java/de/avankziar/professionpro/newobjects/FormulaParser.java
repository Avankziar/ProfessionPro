package main.java.de.avankziar.professionpro.newobjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

public class FormulaParser
{
	private String equation;
	private ArrayList<String> values = new ArrayList<>();
	
	public FormulaParser(){}
	
	public FormulaParser(String equation, ArrayList<String> values)
	{
		setEquation(equation);
		setValues(values);
	}
	
	public Double caculate()
	{
		if(equation == null)
		{
			return null;
		}
		ArrayList<Argument> argumentList = new ArrayList<>();
		for(String value : values)
		{
			argumentList.add(new Argument(value));
		}
		Argument[] argumentArray = new Argument[argumentList.size()];
		argumentArray = argumentList.toArray(argumentArray);
		Expression expression = new Expression(equation, argumentArray);
		return expression.calculate();
	}
	
	public Double caculate(String internString, double internDouble)
	{
		if(equation == null)
		{
			return null;
		}
		ArrayList<Argument> argumentList = new ArrayList<>();
		for(String value : values)
		{
			argumentList.add(new Argument(value));
		}
		argumentList.add(new Argument(internString+" = "+internDouble));
		Argument[] argumentArray = new Argument[argumentList.size()];
		argumentArray = argumentList.toArray(argumentArray);
		Expression expression = new Expression(equation, argumentArray);
		return expression.calculate();
	}
	
	/*
	 * Possible intern changeable Values:
	 * - Level = Level of the Profession | From 1 to "infinity"
	 * - RegisteredPlayers - All Players in the mysql | From 0 to "infinity"
	 * - 
	 */
	public Double caculate(LinkedHashMap<String, Double> internValues)
	{
		if(equation == null)
		{
			return null;
		}
		ArrayList<Argument> argumentList = new ArrayList<>();
		for(String value : values)
		{
			argumentList.add(new Argument(value));
		}
		for(Entry<String, Double> internValue : internValues.entrySet())
		{
			argumentList.add(new Argument(internValue.getKey()+" = "+internValue.getValue()));
		}
		Argument[] argumentArray = new Argument[argumentList.size()];
		argumentArray = argumentList.toArray(argumentArray);
		Expression expression = new Expression(equation, argumentArray);
		return expression.calculate();
	}
	
	/*public static void main(String[] args)
	{
		String equation = "StartValue*(1-e^(-CurveValue*Level))";
		List<String> list = new ArrayList<>();
		list.add("StartValue = 100000");
		list.add("Level = 1");
		list.add("CurveValue = 0.03");
		new CalculationParser(equation, (ArrayList<String>) list).caculate();
	}*/

	public String getEquation()
	{
		return equation;
	}

	public void setEquation(String equation)
	{
		this.equation = equation;
	}

	public ArrayList<String> getValues()
	{
		return values;
	}

	public void setValues(ArrayList<String> values)
	{
		this.values = values;
	}
}
