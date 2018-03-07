/*
synchronized private void saveAllergies() {
        HashSet<Integer> hashSet = new HashSet<>();
        HashSet<Integer> hashSetNotOn = new HashSet<>();
        HashSet<Integer> hashSetPicture = new HashSet<>();
        HashSet<Integer> hashSetPictureNotOn = new HashSet<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
        for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
        if (!preference) {
        if (checkBoxClass.isOn()) {
        hashSet.add(checkBoxClass.getChildKey());
        hashSetPicture.add(checkBoxClass.getParentPicture());
        }else{
        hashSetNotOn.add(checkBoxClass.getChildKey());
        hashSetNotOn.add(checkBoxClass.getParentKey());
        hashSetPictureNotOn.add(checkBoxClass.getParentPicture());
        }
        } else {
        if (checkBoxClass.getParentCheckBox().isChecked()) {
        hashSetPicture.add(checkBoxClass.getParentPicture());
        }else{
        hashSetPictureNotOn.add(checkBoxClass.getParentPicture());
        }
        if (checkBoxClass.isOn()) {
        hashSet.add(checkBoxClass.getChildKey());
        }else{
        hashSetNotOn.add(checkBoxClass.getChildKey());

        }
        }

        }
        }
        for (Integer integer : hashSet) {
        Log.d(TAG, "HASHSET: " + context.getString(integer));
        }

        for (Integer integer : hashSetPicture) {
        Log.d(TAG, "HASHSETpictu: " + integer);
        }
        HashSet<Integer> allergiesToSave = SharedPreferenceClass.getSharedPreference(context, "allergiesToSave", TAG);
        HashSet<Integer> allergiesToRemove = new HashSet<>();
        for (Integer integer : allergiesToSave) {
        try {
        if(context.getString(integer).contains("res/")){
        allergiesToRemove.add(integer);
        }
        }catch (Resources.NotFoundException e){
        allergiesToRemove.add(integer);
        }
        }
        allergiesToSave.removeAll(allergiesToRemove);
        if (!preference) {
        hashSet.addAll( SharedPreferenceClass.getSharedPreference(context, "preferenceSave", TAG));
        Log.d(TAG, "saveAllergies: "+ hashSet);
        hashSet.removeAll(hashSetNotOn);
        Log.d(TAG, "saveAllergies: "+ hashSet);
        hashSetPicture.addAll( SharedPreferenceClass.getSharedPreference(context, "preferenceSavePicture", TAG));
        Log.d(TAG, "saveAllergies: "+ hashSetPicture);
        hashSetPicture.removeAll(hashSetPictureNotOn);
        Log.d(TAG, "saveAllergies: "+ hashSetPicture);
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
        for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
        if (checkBoxClass.getParentCheckBox().isChecked()) {
        hashSet.add(checkBoxClass.getParentKey());
        for (AllergyCheckBoxClass boxClass : checkBoxClass.getNeigbhourClasses()) {
        allergiesToSave.remove(boxClass.getChildKey());
        }
        }
        }
        }
        SharedPreferenceClass.setSharedPreference(context, hashSet, "allergySave", TAG);
        SharedPreferenceClass.setSharedPreference(context, hashSetPicture, "allergySavePicture", TAG);
        HashSet<String> hashSetPictureName = new HashSet<>();
        for (Integer integer : hashSetPicture) {
        hashSetPictureName.add(context.getResources().getResourceEntryName(integer));
        }
        SharedPreferenceClass.setSharedPreference(context, allergiesToSave, "allergiesToSave", TAG);
        SharedPreferenceClass.setSharedPreference(context, hashSetPictureName, "allergySavePictureName", TAG);
        } else {

        HashSet<Integer> allergySave = SharedPreferenceClass.getSharedPreference(context, "allergySave", TAG);
        ArrayList<Integer> parentKeys = AllergyList.getParentKeys();
        hashSetPicture.addAll(SharedPreferenceClass.getSharedPreference(context,"allergySavePicture",TAG));
        hashSet.addAll(SharedPreferenceClass.getSharedPreference(context,"allergySave",TAG));
        for (Integer integer : hashSetNotOn) {
        Log.d(TAG, "HASHSET NOT ON: " + context.getString(integer));
        }

        for (Integer integer : hashSetPictureNotOn) {
        Log.d(TAG, "HASHSETpictuNOT OONB: " + integer);
        }
        boolean open = false;
        for (Integer integer : hashSet) {

        Log.d(TAG, "HASHSET PREFERENCE: " + context.getString(integer));
        open = true;
        }
        for (Integer integer : allergySave) {
        Log.d(TAG, "allergySave: " + context.getString(integer));
        }
        //hashSet.addAll(allergySave);

        for (Integer parentKey : parentKeys) {
        if (!allergySave.contains(parentKey) && hashSet.contains(parentKey)) {
        ArrayList<AllergyList.PictureIngredient> specifiedKey = new AllergyList(context).getSpecifiedKey(parentKey);
        if(specifiedKey != null){
        for (AllergyList.PictureIngredient pictureIngredient : specifiedKey) {
        if(!allergiesToSave.contains(pictureIngredient.getId())){
        hashSet.add(pictureIngredient.getId());
        }
        }
        open = true;
        hashSetPicture.add(specifiedKey.get(0).getPicture());
        }

        } else if (allergySave.contains(parentKey) && hashSetNotOn.contains(parentKey)) {
        ArrayList<AllergyList.PictureIngredient> specifiedKey = new AllergyList(context).getSpecifiedKey(parentKey);
        if(specifiedKey != null){
        for (AllergyList.PictureIngredient pictureIngredient : specifiedKey) {
        if (!allergySave.contains(pictureIngredient.getId())) {
        Log.d(TAG, "ALLERGYSAVE THIS : " + pictureIngredient.getIngredient());
        allergiesToSave.add(pictureIngredient.getId());
        }
        hashSet.remove(pictureIngredient.getId());
        Log.d(TAG, "REMOVE: " + pictureIngredient.getIngredient());
        }
        open = true;
        hashSetPicture.remove(specifiedKey.get(0).getPicture());
        Log.d(TAG, "saveAllergies: " + specifiedKey.get(0).getPicture());
        hashSet.remove(parentKey);
        Log.d(TAG, "REMOVE: " + context.getString(parentKey));
        }

        }

        }
        hashSet.removeAll(hashSetNotOn);
        hashSetPicture.removeAll(hashSetPictureNotOn);
        Log.d(TAG, "saveAllergies: "+ open);
        SharedPreferenceClass.setSharedPreference(context, allergiesToSave, "allergiesToSave", TAG);
        if (open) {
        SharedPreferenceClass.setSharedPreference(context, hashSet, "preferenceSave", TAG);
        SharedPreferenceClass.setSharedPreference(context, hashSetPicture, "preferenceSavePicture", TAG);
        SharedPreferenceClass.setSharedPreference(context, hashSet, "allergySave", TAG);
        SharedPreferenceClass.setSharedPreference(context, hashSetPicture, "allergySavePicture", TAG);
        HashSet<String> hashSetPictureName = new HashSet<>();
        for (Integer integer : hashSetPicture) {
        hashSetPictureName.add(context.getResources().getResourceEntryName(integer));
        }
        SharedPreferenceClass.setSharedPreference(context, hashSetPictureName, "preferenceSavePictureName", TAG);
        }

        }

        }*/
