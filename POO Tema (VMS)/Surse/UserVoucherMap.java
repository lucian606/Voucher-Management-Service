import java.util.ArrayList;

public class UserVoucherMap<K, V> extends ArrayMap<K, ArrayList> {
    public boolean addVoucher(Voucher v) {
        for (ArrayMap.Entry entry : entries) {
            if (entry.getKey().equals(v.getCampaign_ID())) {
                ArrayList<Voucher> newList = new ArrayList<Voucher>();
                newList = (ArrayList<Voucher>) entry.getValue();
                if(newList.contains(v))
                    return false;
                newList.add(v); //Adding the voucher to its corresponding list
                entry.setValue(newList); //Updating the list
                return true;
            }
        }
        ArrayList<Voucher> newID_List=new ArrayList<Voucher>();
        newID_List.add(v);
        super.put((K) v.getCampaign_ID(), newID_List); //New list in case if it's the 1st voucher of the campaign
        return true;
    }
}