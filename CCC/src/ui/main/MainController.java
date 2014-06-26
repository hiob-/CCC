package ui.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.modelController.CurrencyPairController;
import logic.modelController.RuleController;
import logic.updater.CurrencyPairUpdateManager;
import logic.updater.CurrencyPairUpdateManagerThread;
import logic.updater.UpdateResultStatus;
import model.ObserverObjectWrapper;
import model.currency.CurrencyPair;
import model.rule.Rule;
import ui.ColorScheme;
import ui.currencyPairDetail.CurrencyPairDetailViewController;
import ui.currencyPairEdit.CurrencyPairEditController;
import ui.internationalization.Strings;
import ui.preferences.PreferencesController;
import ui.ruleEdit.RuleEditController;
import ui.ruleOverview.RuleOverviewController;

/**
 * 
 * @author Diego Etter
 * @version 1.3
 * 
 */

public class MainController implements Observer {

	private static MainView mainView;
	private static CurrencyPairDetailViewController currPairDetailViewController;
	private static RuleEditController ruleEditController;
	private static RuleOverviewController ruleOverviewController;
	private static PreferencesController preferencesController;

	private static List<SubscribedCPButtonPair> subscribedCPButtonPairs;
	private static CurrencyPair openCurrencyPair;
	private static boolean ruleView = false;
	private static Object preferencesView;

	/**
	 * Init the MainViewController
	 */
	public MainController() {
		subscribedCPButtonPairs = new ArrayList<SubscribedCPButtonPair>();
		initView();
		initDomainControllers();
		initFunctions();
		CurrencyPairUpdateManager.getInstance()
		.getUpdateResultStateObj().addObserver(this);

		setIndicator("Not updated yet",true);

	}

	private void initDomainControllers() {
		final List<CurrencyPair> subscribedCP = CurrencyPairController.getInstance().getAllSubsribedCurrencyPairs();
		for (final CurrencyPair currencyPair : subscribedCP) {
			addExchangeButton(currencyPair);
		}
		CurrencyPairController.getInstance().addObserver(this);
		RuleController.getInstance().addObserver(this);
	}

	private void initView() {
		mainView = new MainView();
		preferencesController = new PreferencesController();
		preferencesView = preferencesController.getPanel();
	}

	private void initFunctions() {
		mainView.setButtonFunction(mainView.getJButton_AddCurrencyPair(),
				new AddNewExchangeListener());
		mainView.setButtonFunction(mainView.getJButton_CurrencyPairValue(),
				new SetValueViewListener());
		mainView.setButtonFunction(mainView.getJButton_CurrencyPairRule(),
				new SetRuleViewListener());
		mainView.setButtonFunction(mainView.getJButton_HelpMenue(),
				new SetHelperViewListener());
		mainView.setButtonFunction(mainView.getJButton_Preferences(),
				new SetPreferencesViewListener());
		mainView.setButtonFunction(mainView.getJButton_CurrencyPairRemove(),
				new RemoveSubscribedCurrencyPairListener());

		mainView.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				System.out.println("CCC over and out");
				Loader.deinitAll();
				System.out.println("DEINIT");
				System.exit(0);
				System.out.println("EXIT");
			}
		});

	}

	public MainView getMainView() {
		return mainView;
	}

	private static void setButtonState(final JButton button, final Boolean state) {
		if (state) {
			button.setBackground(ColorScheme.GOLD);
		} else {
			button.setBackground(null);
		}
	}

	/**
	 * Sets the Indicator button color according to the good param (TRUE = GREEN, FALSE = ORANGE).
	 * @param toolTipMessage
	 * @param good
	 */
	private static void setIndicator(final String toolTipMessage, boolean good) {
		if(good){
			mainView.getJButton_Indicator().setBackground(ColorScheme.ACTIVE_GREEN);
		}else {
			mainView.getJButton_Indicator().setBackground(ColorScheme.INACTIVE_ORANGE);			
		}
		mainView.getJButton_Indicator().setToolTipText(toolTipMessage);
	}



	/**
	 * Change To last opened CurrencyPair Panel
	 */
	private void changeToEmptyCurrencyPair() {
		if (currPairDetailViewController == null) {
			mainView.setEmptyView();
		} else {
			changeToCurrencyPair(openCurrencyPair);
		}
	}

	/**
	 * After Abort a Operation
	 */
	private void cancelExchangeSubscriber() {
		changeToEmptyCurrencyPair();
	}

	/**
	 * 
	 * @param newPair
	 *            : to open a Panel of the CurrencyPair
	 */
	private void changeToCurrencyPair(final CurrencyPair newPair) {
		if (newPair == null) {
			changeToEmptyCurrencyPair();
		} else {
			openCurrencyPair = newPair;
			if (currPairDetailViewController != null) {
				currPairDetailViewController.deleteObserver();
			}
			currPairDetailViewController = new CurrencyPairDetailViewController(
					newPair);
			if (ruleOverviewController != null) {
				ruleOverviewController.DeleteObserver();
			}
			ruleOverviewController = new RuleOverviewController(newPair);
			ActionListener newRule = new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					changeToRuleCreateView();
				}
			};
			
			ActionListener editRule = new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					changeToRuleEditView(ruleOverviewController.getSelectedRule());	
				}
			};
			ruleOverviewController.setEditRuleListener(editRule);
			ruleOverviewController.setNewRuleListener(newRule);
			
			if (ruleView) {
				CurrencyPairRuleView();
			} else {
				CurrencyPairDetailView();
			}
			activateButtonOfCP(newPair);
		}
	}

	/**
	 * If a CP Button is clicked, set default color for all other then the one
	 * clicked (param) Call with null to deactivate all Also removes color of
	 * preferences, since CP button was clicked
	 * 
	 * @param CP
	 *            Button Pair
	 */
	private static void activateButtonOfCP(final CurrencyPair newPair) {
		for (final SubscribedCPButtonPair pair : subscribedCPButtonPairs) {
			setButtonState(pair.getButton(), pair.getCp().equals(newPair));
		}

		mainView.getJButton_Preferences().setBackground(null);
	}

	private void changeToRuleCreateView() {
		ruleEditController = new RuleEditController(openCurrencyPair);
		setRuleEditActionListener(ruleEditController);
		changeToEditView(ruleEditController.getViewObj());
	}

	private void changeToRuleEditView(final Rule rule) {
		ruleEditController = new RuleEditController(rule);
		setRuleEditActionListener(ruleEditController);
		changeToEditView(ruleEditController.getViewObj());
	}
	
	private void setRuleEditActionListener(final RuleEditController ruleEdit){
		ActionListener Cancel = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				changeToEmptyCurrencyPair();
			}
		};
		
		ActionListener Save = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ruleEdit.doSave()){
					changeToEmptyCurrencyPair();
				}else{
					ruleEdit.SaveError();
				}
				
			}
			
		};
		
		ruleEdit.setSaveListener(Save);
		ruleEdit.setCancelListener(Cancel);
	}

	private void addExchangeButton(final CurrencyPair cp) {
		final JButton newButton = mainView.addExchangeButton(cp.toString(),
				new CurrencyPairChoseListener(cp));
		subscribedCPButtonPairs.add(new SubscribedCPButtonPair(cp, newButton));
	}

	private void removeCurrPair(final CurrencyPair toDelcp) {
		final int selection = JOptionPane.showConfirmDialog(null,
				Strings.getString("MainView.CurrPairRemoveMessage"),
				Strings.getString("MainView.CurrPairRemoveTitle"), JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);


		if (selection == JOptionPane.YES_OPTION || selection ==JOptionPane.NO_OPTION) {
			killthread();
			toDelcp.deleteObserver(this);
			 if(selection == JOptionPane.YES_OPTION){
				 removeCurrPairRules(toDelcp);
			 }
			removeCurrPairButton(toDelcp);
			if(!toDelcp.isSubscribed()){
				CurrencyPairUpdateManager.getInstance().getUpdateResultStateObj().removeEntry(toDelcp);							
			}	
		}

	}
	
	private void killthread() {
		CurrencyPairUpdateManagerThread thread = CurrencyPairUpdateManager.getInstance().getThread(openCurrencyPair);
		
		CurrencyPairController.getInstance().removeSuscribedCurrencyPair(openCurrencyPair);
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(thread.isAlive()){
			thread.interrupt();
		}		
	}

	private void removeCurrPairRules(final CurrencyPair toDelcp){
		final ArrayList<Rule> ruleList = RuleController.getInstance()
				.getRuleList();
		Rule toDelRule = null;
		for (final Rule rule : ruleList) {
			if (rule.getCurrencyPair().equals(toDelcp)) {
				toDelRule=rule;
			}
		}
		if(toDelRule != null){RuleController.getInstance().removeRule(toDelRule);}
	}
	
	private void removeCurrPairButton(final CurrencyPair toDelcp){
		SubscribedCPButtonPair toDelPair = null;
		for (final SubscribedCPButtonPair pair : subscribedCPButtonPairs) {
			if (pair.getCp().equals(toDelcp)) {
				toDelPair = pair;
			}
		}
		mainView.removeCurrencyPair(toDelPair.getButton());
		subscribedCPButtonPairs.remove(toDelPair);
		mainView.setEmptyView();
	}
	

	/**
	 * Shows the ValueWindow of the Chosen CurrencyPair
	 */
	private void CurrencyPairDetailView() {
		if (openCurrencyPair == null) {
			return;
		}
		mainView.highlightValueButton();
		mainView.resetHightRuleButton();
		changeToCurrPairView(currPairDetailViewController.getPanel());
		ruleView = false;
	}

	private void PreferencesView() {
		changeToView((JPanel) preferencesView);
		activateButtonOfCP(null); // remove color of all
		mainView.getJButton_Preferences().setBackground(ColorScheme.GOLD); 
		// set pref color                                ^
	}

	/**
	 * Shows the RuleWindow of the Chosen CurrencyPair
	 */
	private void CurrencyPairRuleView() {
		if (openCurrencyPair == null) {
			return;
		}
		mainView.highlightRuleButton();
		mainView.resetHightValueButton();
		changeToCurrPairView(ruleOverviewController.getPanel());
		ruleView = true;
	}

	private void AddExchangeView() {
		final CurrencyPairEditController currPairEditController = new CurrencyPairEditController();
		final ActionListener Save = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CurrencyPair CP = currPairEditController.SubscribeCurrPair();
				if(CP!=null){
					changeToCurrencyPair(CP);
				}	
			}
		};
		currPairEditController.setSaveCurrPairListener(Save);
		final ActionListener Cancel = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				changeToEmptyCurrencyPair();
			}
			
		};
		currPairEditController.setCancelCurrPairListener(Cancel);
		changeToEditView(currPairEditController.getViewObject());
	}

	private void changeToCurrPairView(final JPanel newCurrPairPanel) {
		mainView.setCurrPairView(newCurrPairPanel);
	}

	private void changeToView(final JPanel newPanel) {
		mainView.setView(newPanel);
	}

	private void changeToEditView(final JPanel newPanel) {
		mainView.setEditView(newPanel);
	}

	class AddNewExchangeListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			AddExchangeView();
		}

	}

	class SetValueViewListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			CurrencyPairDetailView();
		}
	}

	class SetRuleViewListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			CurrencyPairRuleView();
		}
	}

	class SetPreferencesViewListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			PreferencesView();
		}
	}
	


	class SetHelperViewListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			JOptionPane.showMessageDialog(null,
				    "<html><h1>Coin Control Center</h1><br>" +
				    "Written by: Oussama Zgheb, Luca T�nnler, Diego Etter<br>" +
				    "Licence: TODO" +
				    "</html>",  
				    "CCC",
				    JOptionPane.PLAIN_MESSAGE);
			
			printDebugMemoryUsage();
		}

		private void printDebugMemoryUsage() {
			int mb = 1024*1024;
			
			//Getting the runtime reference from system
			Runtime runtime = Runtime.getRuntime();
			
			System.out.println("##### Heap utilization statistics [MB] #####");
			
			//Print used memory
			System.out.println("Used Memory:" 
				+ (runtime.totalMemory() - runtime.freeMemory()) / mb);

			//Print free memory
			System.out.println("Free Memory:" 
				+ runtime.freeMemory() / mb);
			
			//Print total available memory
			System.out.println("Total Memory:" + runtime.totalMemory() / mb);

			//Print Maximum available memory
			System.out.println("Max Memory:" + runtime.maxMemory() / mb);			
		}

	}

	class CurrencyPairChoseListener implements ActionListener {

		CurrencyPair cp;

		public CurrencyPairChoseListener(final CurrencyPair cp) {
			this.cp = cp;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			changeToCurrencyPair(cp);
		}

	}

	class RemoveSubscribedCurrencyPairListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			removeCurrPair(openCurrencyPair);	
		}
	}
	
	

	public boolean checkIfCpAlreadyExists(final CurrencyPair cp) {
		for (final SubscribedCPButtonPair pairs : subscribedCPButtonPairs) {
			if (pairs.getCp().equals(cp)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void update(final Observable arg0, final Object arg1) {
		if (arg1 instanceof ObserverObjectWrapper) {
			final ObserverObjectWrapper wrapper = (ObserverObjectWrapper) arg1;
			
			switch (wrapper.getObject().getClass().getSimpleName()) {
			case "CurrencyPair":
				wrapperCP(wrapper);
				break;
			case "Rule":
				if (ruleView) {
					CurrencyPairRuleView();
				}
				break;
			case "ConcurrentHashMap":
				if (arg0 instanceof UpdateResultStatus) {
					updateIndicator(wrapper);
				}
				break;
			}

		}

	}

	private void updateIndicator(final ObserverObjectWrapper wrapper) {
		@SuppressWarnings("unchecked")
		final ConcurrentHashMap<String, Integer> states = (ConcurrentHashMap<String, Integer>) wrapper.getObject();
		final Iterator it = states.entrySet().iterator();
		boolean validState = true;
		String statesString = "";
		while (it.hasNext()) {
			final Map.Entry pairs = (Map.Entry) it.next();
			String stateString="<b>";
			stateString = getStateString(pairs, stateString);
			stateString+="</b>";
			statesString += stateString+" "+pairs.getKey() + "<br>";
			if ((int) pairs.getValue() > 0) {
				validState = false;
			}
		}
		String lastUpdateString = "<b>Last Update</b> ["+getCurrentTime()+"]<br>" ;
		String finalToolTip = "<html>"+lastUpdateString + statesString+"</html>";
		setIndicator(finalToolTip,validState);		
	}

	private String getStateString(final Map.Entry pairs, String stateString) {
		switch((int)pairs.getValue()){
		case 0:
			stateString="[OK]";
			break;
		case 1:
			stateString="[NO CONNECTION]";
			break;
		case 2:				
			stateString="[TIMEOUT]";
			break;
		}
		return stateString;
	}

	private String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	return sdf.format(cal.getTime());
	}

	private void wrapperCP(final ObserverObjectWrapper wrapper) {
		final CurrencyPair cp = (CurrencyPair) wrapper.getObject();
		switch (wrapper.getActionType()) {
		case SUBSCRIBE:
			if (!checkIfCpAlreadyExists(cp)) {
				addExchangeButton(cp);
			}
			break;
		case UNSUBSCRIBE:
			if(!cp.isSubscribed()){
				CurrencyPairUpdateManager.getInstance().getUpdateResultStateObj().removeEntry(cp);							
			}
			System.out.println("maincontroller -> unsubscribe cp");
			break;
		default:
			break;
		}
	}
}
