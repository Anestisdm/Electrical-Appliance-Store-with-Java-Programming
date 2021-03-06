import java.util.HashMap;
import java.util.Locale;
import java.lang.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public abstract class Appliance {

private static int count=0;
private final String code;
private final String model_name;
private final int model_year;
private final String manufacturer;
private double price;
private int stock;

private static HashMap<Integer,Appliance> Appliances = new HashMap<Integer,Appliance>();
private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

public Appliance(String code,String model_name, int model_year, String manufacturer, double price, int stock) {
	count++;
	Appliances.put(count, this);
	this.code = code;
	this.model_name = model_name;
	this.model_year = model_year;
	this.manufacturer = manufacturer;
	this.price = price;
	this.stock=stock;
}

public abstract double getDiscount();
public abstract String getName();

public String getModel_name() {
	return model_name;
}

public String getCode() {
	return code;
}
public int getStock() {
	return stock;
}

public double getPrice() {
	return price;
}

public int getModel_year() {
	return model_year;
}

public String getManufacturer() {
	return manufacturer;
}


public double getDiscountPrice() {
	double discount_price=Math.round((this.price-(this.price*this.getDiscount()))*100.0/100.0);
	return discount_price;
}


public static HashMap<Integer, Appliance> getAppliances() {
	return Appliances;
}


@Override
public String toString() {
	return "\n������� ���������=" + code + "\n����� ��������=" + model_name +"\n������ ����=" + price+"�"+"\n�������="+Math.round(getDiscount()*100)+"%"+"\n������ ����="+getDiscountPrice()+"�"+"\n�������="+stock+" ��������"+ "\n���� ��������=" + model_year + "\n�������������="
			+ manufacturer ;
}

public static void Print_Appliances(String Device) {
	Scanner scanner= new Scanner(System.in);
	int c=0;
	HashMap<Integer,Appliance>Selected_Appliances=new HashMap<Integer,Appliance>();
	for(int i=0; i<=Appliances.size();i++) {
		if (Appliances.get(i)!=null) {
			if (Appliances.get(i).getName().equalsIgnoreCase (Device)) {
				c++;
				Selected_Appliances.put(c, Appliances.get(i));
				System.out.println("-----------------------------------------------------------");
				System.out.println(c+"."+Appliances.get(i).getModel_name());
			}
		}
	}
	System.out.println("-----------------------------------------------------------");
	System.out.print("���� ������� (������): ");
	int input=scanner.nextInt();
	System.out.println("-----------------------------------------------------------");
	System.out.println(Selected_Appliances.get(input));
	System.out.println("-----------------------------------------------------------");
	if (Selected_Appliances.get(input).stock>0) {
		boolean x=false;
		do {
		System.out.println("�� ������������ ������� ���� ������� ��� ��������� ���. ������ �� ���������������� �����; (1:��� � 2:���)");
		System.out.print("���� ������� (������): ");
			int input2=scanner.nextInt();
			if (input2==1) {
				System.out.print("�������� �������� ������ ��������� ���� ������ �� ���������: (������� 0 ��� �����) ");
				int pieces=scanner.nextInt();
				if (pieces==0) {
					break;
				}
				if (pieces<=Selected_Appliances.get(input).stock) {
					Scanner in = new Scanner(System.in);
					System.out.print("�������� �������� �� ������������� ���: [(�� ������� ������������) � ������� 0 ��� �����] ");
					String name=in.nextLine();
					if (name.equals("0")) {
						break;
					}
					System.out.print("�������� �������� �� �������� ���: [(�� 6947452542) � ������� 0 ��� �����] ");
					String tel=in.nextLine();
					if (tel.equals("0")) {
						break;
					}
					double sale_cost=(Selected_Appliances.get(input).getDiscountPrice())*pieces;
					Sale s1= new Sale (Selected_Appliances.get(input),name, LocalDate.now(), tel,sale_cost,pieces);
					Selected_Appliances.get(input).stock-=pieces;
					System.out.println("� ����� ������������ ��������!");
					System.out.println("-----------------------------------------------------------");
					System.out.println(s1);
					x=true;
				}
				else {
					System.out.println("��� ������� ������ ������� ��� ��������� ��� ��� �� ������������ ��� ����� ���!");
					break;
				}
			}
			else if (input2==2) {
				x=true;
			}
			else {
				System.out.println("����� ��������!");
			}
			}
			while (x==false);
	}
	else {
		try {
			boolean x=false;
			do {
				System.out.println("��� ������� ������� ��� �� ������������ ������� ��� ��������� ���. ������ �� ���������������� ����������; (1:��� � 2:���)");
				System.out.print("���� ������� (������): ");
				int input2=scanner.nextInt();
				if (input2==1) {
					Scanner in = new Scanner(System.in);
					System.out.print("�������� �������� ������ ��������� ���� ������ �� ������������: (������� 0 ��� �����)");
					int pieces=scanner.nextInt();
					if (pieces==0) {
						break;
					}
					System.out.print("�������� �������� �� ������������� ���: [(�� ������� ������������) � ������� 0 ��� �����] ");
					String name=in.nextLine();
					if (name.equals("0")) {
						break;
					}
					System.out.print("�������� �������� �� �������� ���: [(�� 6947452542) � ������� 0 ��� �����] ");
					String tel=in.nextLine();
					if (tel.equals("0")) {
						break;
					}
					System.out.print("�������� �������� ����������� ���������� ������: [(�� 2020-05-11) � ������� 0 ��� �����] ");
					LocalDate arrival_date=LocalDate.parse(in.nextLine(),formatter);
					if (arrival_date.equals("0")) {
						break;
					}
					double order_cost=(Selected_Appliances.get(input).getDiscountPrice())*pieces;
					Order o1=new Order(Selected_Appliances.get(input),name,tel, LocalDate.now(), arrival_date, order_cost ,pieces,"EXPECTED");
					System.out.println("� ���������� ������������ ��������!");
					System.out.println("-----------------------------------------------------------");
					System.out.println(o1);
					x=true;
				}
				else if (input2==2) {
					x=true;
				}
				else {
					System.out.println("����� ��������!");
				}
			}
			while (x==false);
			}
		catch (DateTimeParseException ex) {
			System.out.println("Wrong date format! Try again!");
		}
	}
}





public static void Overview_Appliances() {
	Scanner scanner= new Scanner(System.in);
	boolean x=false;
	do {
	System.out.println("-----------------------------------------------------------");
	System.out.println("0.���������");
	System.out.println("1.���� ��� ������");
	System.out.println("2.Gaming");
	System.out.println("3.�������� ��������");
	System.out.println("-----------------------------------------------------------");
	System.out.print("���� ������� (������): ");
	int input=scanner.nextInt();
		switch (input) {
		case 0 :
			x=true;
			break;
		case 1 :
			x=true;
			boolean y=false;
			do {
				System.out.println("-----------------------------------------------------------");
				System.out.println("0.���������\n1.�����������\n2.Blue ray/DVD players\n3.������������ �������");
				System.out.println("-----------------------------------------------------------");
				System.out.print("���� ������� (������): ");
				int input2=scanner.nextInt();
				switch (input2) {
				case 0:
					x=false;
					y=true;
					break;
				case 1:
					Print_Appliances("tv");
					y=true;
					break;
				case 2:
					Print_Appliances("DVD player");
					y=true;
					break;
				case 3:
					Print_Appliances("Camera");
					y=true;
					break;
				default:
					System.out.println("-----------------------------------------------------------");
					System.out.println("����� ��������");
				}
			}
			while(y==false);
		break;
		case 2:
			x=true;
			y=false;
			do {
				System.out.println("-----------------------------------------------------------");
				System.out.println("0.���������\n1.��������/������� ��������");
				System.out.println("-----------------------------------------------------------");
				System.out.print("���� ������� (������): ");
				int input2=scanner.nextInt();
				switch (input2) {
				case 0:
					x=false;
					y=true;
					break;
				case 1:
					Print_Appliances("Console");
					y=true;
					break;
				default:
					System.out.println("-----------------------------------------------------------");
					System.out.println("����� ��������");
				}
			}
			while(y==false);
		break;
		case 3:
			x=true;
			y=false;
			do {
				System.out.println("-----------------------------------------------------------");
				System.out.println("0.���������\n1.������\n2.��������� ������");
				System.out.println("-----------------------------------------------------------");
				System.out.print("���� ������� (������): ");
				int input2=scanner.nextInt();
				switch (input2) {
				case 0:
					x=false;
					y=true;
					break;
				case 1:
					Print_Appliances("refrigerator");
					y=true;
					break;
				case 2:
					Print_Appliances("Washing Machine");
					y=true;
					break;
				default:
					System.out.println("-----------------------------------------------------------");
					System.out.println("����� ��������");
				}
			}
			while(y==false);
		break;
		default:
			System.out.println("-----------------------------------------------------------");
			System.out.println("����� ��������");
		}
	}
	while(x==false);
		}




}



