package model;

/**
 * This class enables the notify method of an observable to pass a status and a value to the observer
 * 
 * @author Luca Tännler
 * @version 1.0
 * */
public class ObserverObjectWrapper {
	public static enum ActionType {
		MODIFICATION, REMOVE, ADD, UNSUBSCRIBE, SUBSCRIBE
	};

	private final Object object;
	private final ActionType action;

	public ObserverObjectWrapper(final Object pObject,
			final ActionType pActionType) {
		object = pObject;
		action = pActionType;
	}

	public Object getObject() {
		return object;
	}

	public ActionType getActionType() {
		return action;
	}

}
