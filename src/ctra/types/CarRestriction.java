package ctra.types;

public class CarRestriction
{
	private String carName;
	private int addedMass;
	private int intakeRestriction;
	
	public CarRestriction(String carName,int addedMass,int intakeRestriction)
	{
		this.carName=carName;
		this.addedMass=addedMass;
		this.intakeRestriction=intakeRestriction;
	}

	public String getCarName()
	{
		return carName;
	}

	public int getAddedMass()
	{
		return addedMass;
	}

	public int getIntakeRestriction()
	{
		return intakeRestriction;
	}
}
