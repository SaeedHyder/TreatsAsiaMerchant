package com.app.merchanttreatzasia.interfaces;

import com.app.merchanttreatzasia.entities.BranchEnt;
import com.app.merchanttreatzasia.entities.TypeEnt;

/**
 * Created by khan_muhammad on 11/14/2017.
 */

public interface ItemsClickListener {

    void editClick(BranchEnt branchEnt);
    void deleteClick(BranchEnt branchEnt);

}
