/*
@layer: Gateway
 */

package Gateways;

import Entities.PanelEvent;
import Entities.PartyEvent;
import Entities.TalkEvent;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class EventFile implements FileInteractable {

    private final String talkpath = "file/talks.ser";
    private final String partypath = "file/parties.ser";
    private final String panelpath = "file/panels.ser";
    private final String idpath = "file/eventids.ser";

    /**
     * Save all the talkEvents to a file
     * Should **only** be use when program shutting down
     * @param objs list of talkEvents to be saved
     */
    public void saveTalks(ArrayList<TalkEvent> objs) {
        save(objs, talkpath);
    }

    /**
     * load all the previous TalksEvents stored in a file
     * @return a list of TalksEvents
     */
    public ArrayList<TalkEvent> loadTalks() {
        return (ArrayList<TalkEvent>) loadList(talkpath);
    }

    /**
     * Save all the PartyEvents to a file
     * Should **only** be use when program shutting down
     * @param objs list of PartyEvents to be saved
     */
    public void saveParties(ArrayList<PartyEvent> objs) {
        save(objs, partypath);
    }

    /**
     * load all the previous PartyEvents stored in a file
     * @return a list of PartyEvents
     */
    public ArrayList<PartyEvent> loadParties() {
        return (ArrayList<PartyEvent>) loadList(partypath);
    }

    /**
     * Save all the PanelEvents to a file
     * Should **only** be use when program shutting down
     * @param objs list of PanelEvents to be saved
     */
    public void savePanels(ArrayList<PanelEvent> objs) {
        save(objs, panelpath);
    }

    /**
     * load all the previous PanelEvents stored in a file
     * @return a list of PanelEvents
     */
    public ArrayList<PanelEvent> loadPanels() {
        return (ArrayList<PanelEvent>) loadList(panelpath);
    }

    /**
     * Save all the EventIDs to a file
     * Should **only** be use when program shutting down
     * @param objs list of EventIDs to be saved
     */
    public void saveID(ArrayList<Integer> objs) {
        save(objs, idpath);
    }

    /**
     * load all the previous EventIDs stored in a file
     * @return a list of EventIDs
     */
    public ArrayList<Integer> loadID() {
        return (ArrayList<Integer>) loadList(idpath);
    }

}

