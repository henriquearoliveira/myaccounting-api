package br.com.contability.comum;

import org.springframework.ui.Model;

public class ModelConstruct {
	
	/**
	 * @param model
	 * @param optionMenu
	 * @param optionSubMenu
	 * 
	 * Injeta no model da tela as informações do menu, deixando-o ativo.
	 */
	public static void setAttributes(Model model,String optionMenu, String optionSubMenu){
		
		model.addAttribute(optionMenu, "active treeview");
		model.addAttribute(optionSubMenu, "active");
		
	}

}
