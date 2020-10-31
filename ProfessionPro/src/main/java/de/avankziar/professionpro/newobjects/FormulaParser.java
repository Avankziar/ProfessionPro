package main.java.de.avankziar.professionpro.newobjects;

import java.util.ArrayList;

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
		double result = expression.calculate();
		//System.out.println(equation+" = "+result);
		return result;
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
