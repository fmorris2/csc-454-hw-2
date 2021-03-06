package delta;

import java.util.Arrays;

import utils.ChangeUtils;
import vending_machine.VendingMachine;
import vending_machine.data.VMInput;

public class VMStateChangeFunction {
	public static void execute(VendingMachine vendingMachine, VMInput[] inputBag) {
		checkForChangeAndCoffee(vendingMachine); //change & coffee
		Arrays.stream(inputBag).forEach(i -> i.INPUT_EFFECT.accept(vendingMachine)); //input
	}
	
	private static void checkForChangeAndCoffee(VendingMachine vendingMachine) {
		if(vendingMachine.isChangePressed()) {
			Arrays.stream(ChangeUtils.calculateChange(vendingMachine))
				.map(output -> Arrays.stream(VMInput.values()).filter(i -> i.name().equals(output.name())).findFirst().get())
				.forEach(coin -> vendingMachine.changeCoinStock(coin, -1));
			vendingMachine.setChangePressed(false);
		}
		while(vendingMachine.getValue() >= VendingMachine.COFFEE_PRICE) {
			vendingMachine.purchaseCoffee();
		}
	}
}
