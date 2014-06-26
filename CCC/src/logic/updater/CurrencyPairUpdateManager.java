package logic.updater;

import java.lang.Thread.State;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import logic.logEngine.Logger;
import logic.modelController.CurrencyPairController;
import model.ObserverObjectWrapper;
import model.currency.CurrencyPair;

/**
 * 
 * Controlls the update thread.
 * 
 * @author Luca Tännler
 * @version 1.0
 * */
public class CurrencyPairUpdateManager implements Observer {
	private static CurrencyPairUpdateManager instance;
	private static int updateInterval = 5000;
	private final UpdateResultStatus updateResultStateObj;

	private final Map<CurrencyPair, ConcurrencyPair> concurrencyPairMap;
	private final Map<CurrencyPair, CurrencyPairUpdateManagerThread> cpThreadMap;

	private CurrencyPairUpdateManager() {
		concurrencyPairMap = new ConcurrentHashMap<CurrencyPair, ConcurrencyPair>();
		cpThreadMap = new ConcurrentHashMap<CurrencyPair, CurrencyPairUpdateManagerThread>();
		updateResultStateObj = new UpdateResultStatus();

		initData();
	}

	private void initData() {
		// Add all subscribed CurrencyPairs to the Update-Thread
		if (!CurrencyPairController.getInstance()
				.getAllSubsribedCurrencyPairs().isEmpty()) {
			for (final CurrencyPair cp : CurrencyPairController.getInstance().getAllSubsribedCurrencyPairs()) {
				addCurrencyPair(cp);
			}
		}
		// Add this instance as Observer from CurrencyPairLibrary
		CurrencyPairController.getInstance().addObserver(this);
	}
	
	private void createCPThread(ConcurrencyPair pConcurrencyPair){
		CurrencyPairUpdateManagerThread newThread = new CurrencyPairUpdateManagerThread(pConcurrencyPair);
		newThread.setUpdateInterval(updateInterval);
		newThread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(final Thread t, final Throwable e) {
				e.printStackTrace();
				Logger.write("Uncuaght Exception in Thread: " + t.getName()
						+ "(ID:" + t.getId() + "; State:" + t.getState()
						+ "). Message: " + e.getMessage(), Logger.LogLevel.error);
			}
		});
		cpThreadMap.put(pConcurrencyPair.getCurrencyPair(), newThread);
	}

	/** Starts the Thread */
	public synchronized void runThread(CurrencyPair pCurrencyPair) {
		CurrencyPairUpdateManagerThread thread = cpThreadMap.get(pCurrencyPair);
		if (thread != null && !thread.isAlive() && thread.getState() == State.NEW) {
			thread.setUpdateInterval(updateInterval);
			thread.start();
		}
	}
	
	public synchronized void runAllThreads(){
		if(!cpThreadMap.isEmpty()){
			for(CurrencyPairUpdateManagerThread thread : cpThreadMap.values()){
				thread.start();
			}
		}
	}

	/**
	 * Check if the thread is running.
	 * 
	 * @return boolean true = Thread is running; false = Thread is not running
	 * */
	public boolean isAlive(CurrencyPair pCurrencyPair) {
		CurrencyPairUpdateManagerThread thread = cpThreadMap.get(pCurrencyPair);
		return thread.isAlive();
	}

	/**
	 * Requests the Thread to stop. (Thread will finish his current
	 * Iteration-Step before ending)
	 * */
	public synchronized void killThread(CurrencyPair pCurrencyPair) {
		CurrencyPairUpdateManagerThread thread = cpThreadMap.get(pCurrencyPair);
		if (thread.isAlive() && thread.getState() != State.TERMINATED) {
			thread.stopThread();
		}
		return;
	}
	
	public synchronized void killAllThreads(){
		if(!cpThreadMap.isEmpty()){
			for(CurrencyPairUpdateManagerThread thread : cpThreadMap.values()){
				thread.stopThread();
			}
		}
	}
	
	public synchronized boolean hasActiveThread(){
		if(!cpThreadMap.isEmpty()){
			for(CurrencyPairUpdateManagerThread thread : cpThreadMap.values()){
				if(!thread.getState().toString().equals("TERMINATED")){
					return true;
				}
			}
		}
		return false;
	}

	public void addCurrencyPair(final CurrencyPair pCurrencyPair) {
		if (!containsCurrencyPair(pCurrencyPair)) {
			final ConcurrencyPair ccp = new ConcurrencyPair(pCurrencyPair);
			concurrencyPairMap.put(pCurrencyPair, ccp);
			createCPThread(ccp);
		}
	}

	public CurrencyPair removeCurrencyPair(final CurrencyPair pCurrencyPair) {
		concurrencyPairMap.remove(pCurrencyPair);
		killThread(pCurrencyPair);
		cpThreadMap.remove(pCurrencyPair);
		return pCurrencyPair;
	}

	public boolean containsCurrencyPair(final CurrencyPair pCurrencyPair) {
		return concurrencyPairMap.containsKey(pCurrencyPair);
	}

	public ConcurrencyPair getConcurrencyPair(final CurrencyPair pCurrencyPair) {
		return concurrencyPairMap.get(pCurrencyPair);
	}

	@Override
	public void update(final Observable o, final Object arg) {
		// Add/Remove CurrencyPair to the Thread
		if (arg instanceof ObserverObjectWrapper
				&& (o instanceof CurrencyPairController)) {
			final ObserverObjectWrapper oow = (ObserverObjectWrapper) arg;
			if (oow.getObject() instanceof CurrencyPair) {
				handleCPU(oow);
			}
		}
	}

	private void handleCPU(final ObserverObjectWrapper oow) {
		final CurrencyPair cp = (CurrencyPair) oow.getObject();
		switch (oow.getActionType()) {
		case SUBSCRIBE:
			addCurrencyPair(cp);
			cpThreadMap.get(cp).start();
			break;
		case UNSUBSCRIBE:
			removeCurrencyPair(cp);
			break;
		case MODIFICATION:
			break;
		default:
			break;
		}
	}

	public CurrencyPairUpdateManagerThread getThread(CurrencyPair pCurrencyPair) {
		return cpThreadMap.get(pCurrencyPair);
	}

	public static CurrencyPairUpdateManager getInstance() {
		if (instance == null) {
			instance = new CurrencyPairUpdateManager();
		}
		return instance;
	}

	public static void setUpdateInterval(final int pupdateInterval) {
		updateInterval = pupdateInterval * 1000;
	}
	
	public synchronized void addUpdateResultState(final CurrencyPair pCurrencyPair, final int pState){
		updateResultStateObj.addState(pCurrencyPair, pState);
	}
	
	public synchronized UpdateResultStatus getUpdateResultStateObj(){
		return updateResultStateObj;
	}
}
