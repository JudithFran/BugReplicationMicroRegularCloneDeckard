import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Judith
 */

class CodeFragment {

    int revision = -1;
    String filepath = "";
    int startline = -1, endline = -1;
    String changetype = "-1";

    String[] lines = new String[10000];

    public void getFragment() {

        String abs_filepath = InputParameters.pathRegular + revision + "/" + filepath;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(abs_filepath)));
            String str = "";

            int line = 0;
            int i = 0;
            while ((str = br.readLine()) != null) {
                line++;
                if (line > endline) {
                    break;
                }
                if (line >= startline && line <= endline) {
                    lines[i] = str.trim();
                    //System.out.println(lines[i]);
                    i++;
                }
            }
        } catch (Exception e) {
            System.out.println("error.getFragment." + e);
        }

    }

    public void showFragment() {

        String abs_filepath = InputParameters.pathRegular + revision + "/" + filepath;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(abs_filepath)));
            String str = "";

            System.out.println("\n" + revision + ": " + filepath + ", " + startline + " - " + endline);
            //System.out.println("---------------------------------------------------");
            int line = 0;
            int i = 0;
            while ((str = br.readLine()) != null) {
                line++;
                if (line > endline) {
                    break;
                }
                if (line >= startline && line <= endline) {
                    lines[i] = str.trim();
                    i++;
                    //System.out.println(str);
                }
            }
            //System.out.println("---------------------------------------------------");
        } catch (Exception e) {
            System.out.println("error.showFragment." + e);
        }
    }
}


public class BugReplicationMicroRegularClones {

    DBConnect db = new DBConnect();
    CompareChanges cc = new CompareChanges();

    int countRevR = 0;
    int countRevRepR = 0;

    int countRevM = 0;
    int countRevRepM = 0;

    int RQ4 = 0;

    public String getBugFixCommits() {
        String bugFixCommits = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(InputParameters.systemName + " commitlog.txt")));

            String str = "";
            String prevString = "";

            int commit = 0;
            while ((str = br.readLine()) != null) {
                if (str.trim().length() == 0) {
                    continue;
                }

                if (prevString.contains("--------------------------------")) {
                    //this is the starting of a commit report.
                    //we need to know the commit number.
                    String str1 = str.trim().split("[ ]+")[0].trim();
                    str1 = str1.substring(1);
                    commit = Integer.parseInt(str1);
                    //System.out.println (commit);
                } else {
                    //according to the study of Mockus
                    if (str.toLowerCase().contains("bug") || str.toLowerCase().contains("fix") || str.toLowerCase().contains("fixup") || str.toLowerCase().contains("error") || str.toLowerCase().contains("fail"))
                    //if (str.contains ("bug") || str.contains("fix") || str.contains ("fixup") || str.contains ("error") || str.contains ("fail"))
                    {
                        if (!bugFixCommits.contains(" " + commit + " ")) {
                            bugFixCommits += " " + commit + " ";
                        }
                    }
                }
                prevString = str;
            }
            br.close();
            //System.out.println ("Revisions that were created because of a bug fix = " + bugFixCommits);

        } catch (Exception e) {
            System.out.println("error in getBugFixCommits = " + e);
        }

        return bugFixCommits;
    }

    // For RQ4 use this method

    public String getBugFixCommitsRQ4() {
        String bugFixCommits = "";
        try{
            String[] bugFixCommitsMockus = new String[10000];
            String[] bugFixCommitsLamkanfi = new String[10000];
            String[] bugFixCommitsTemp = new String[10000];

            String str1 = getBugFixCommitsMockus();
            System.out.println ("Revisions that were created because of a bug fix (Mockus) = " + str1);
            bugFixCommitsMockus = str1.trim().split("  ");

            for(int i = 0; i < bugFixCommitsMockus.length; i++)
                System.out.println ("Revisions that were created because of a bug fix in bugFixCommitsMockus["+i+"] array (Mockus) = " + bugFixCommitsMockus[i]);

            String str2 = getBugFixCommitsLamkanfi();
            System.out.println ("Revisions that were created because of a bug fix (Lamkanfi) = " + str2);
            bugFixCommitsLamkanfi = str2.trim().split("  ");

            for(int i = 0; i < bugFixCommitsLamkanfi.length; i++)
                System.out.println ("Revisions that were created because of a bug fix in bugFixCommitsLamkanfi["+i+"] array (Lamkanfi) = " + bugFixCommitsLamkanfi[i]);

            // Finding common commits in both arrays

            for (int i = 0; i < bugFixCommitsMockus.length; i++) {
                for (int j = 0; j < bugFixCommitsLamkanfi.length; j++) {
                    if (bugFixCommitsMockus[i].equals(bugFixCommitsLamkanfi[j])) {
                        // got the duplicate element
                        //if (!bugFixCommits.contains(" " + bugFixCommitsMockus[i] + " ")) {
                        bugFixCommits += " " + bugFixCommitsMockus[i] + " ";
                        //}
                    }
                }
            }
            System.out.println ("Revisions that were created because of a bug fix (Mockus and Lamkanfi) = " + bugFixCommits);

            bugFixCommitsTemp = bugFixCommits.trim().split("  ");

            for(int i = 0; i < bugFixCommitsTemp.length; i++)
                System.out.println ("Revisions that were created because of a bug fix in bugFixCommitsTemp["+i+"] array (Temp) = " + bugFixCommitsTemp[i]);


        } catch (Exception e) {
            System.out.println("error in getBugFixCommits = " + e);
        }

        return bugFixCommits;
    }

    public String getBugFixCommitsMockus() {
        String bugFixCommits = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(InputParameters.systemName + " commitlog.txt")));
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("brlcad commitlog.txt"))); // Have to make it variable
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ctags commitlog.txt"))); // Have to make it variable

            String str = "";
            String prevString = "";

            int commit = 0;
            while ((str = br.readLine()) != null) {
                if (str.trim().length() == 0) {
                    continue;
                }

                if (prevString.contains("--------------------------------")) {
                    //this is the starting of a commit report.
                    //we need to know the commit number.
                    String str1 = str.trim().split("[ ]+")[0].trim();
                    str1 = str1.substring(1);
                    commit = Integer.parseInt(str1);
                    //System.out.println (commit);
                } else {
                    //according to the study of Mockus
                    if (str.toLowerCase().contains("bug") || str.toLowerCase().contains("fix") || str.toLowerCase().contains("fixup") || str.toLowerCase().contains("error") || str.toLowerCase().contains("fail"))
                    //if (str.contains ("bug") || str.contains("fix") || str.contains ("fixup") || str.contains ("error") || str.contains ("fail"))
                    {
                        if (!bugFixCommits.contains(" " + commit + " ")) {
                            bugFixCommits += " " + commit + " ";
                        }
                    }
                }
                prevString = str;
            }
            br.close();
            //System.out.println ("Revisions that were created because of a bug fix = " + bugFixCommits);

        } catch (Exception e) {
            System.out.println("error in getBugFixCommitsMockus = " + e);
        }

        return bugFixCommits;
    }

    public String getBugFixCommitsLamkanfi() {
        String bugFixCommits = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(InputParameters.systemName + " commitlog.txt")));
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("brlcad commitlog.txt"))); // Have to make it variable
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ctags commitlog.txt"))); // Have to make it variable

            String str = "";
            String prevString = "";

            int commit = 0;
            while ((str = br.readLine()) != null) {
                if (str.trim().length() == 0) {
                    continue;
                }

                if (prevString.contains("--------------------------------")) {
                    //this is the starting of a commit report.
                    //we need to know the commit number.
                    String str1 = str.trim().split("[ ]+")[0].trim();
                    str1 = str1.substring(1);
                    commit = Integer.parseInt(str1);
                    //System.out.println (commit);
                } else {

                    // Severe bugs according to Lamkanfi
                    if (str.toLowerCase().contains("fault") || str.toLowerCase().contains("machin") || str.toLowerCase().contains("reboot") || str.toLowerCase().contains("reinstal") || str.toLowerCase().contains("lockup") || str.toLowerCase().contains("seemingli") || str.toLowerCase().contains("perman") || str.toLowerCase().contains("instantli") || str.toLowerCase().contains("segfault") || str.toLowerCase().contains("compil")
                            || str.toLowerCase().contains("hang") || str.toLowerCase().contains("freez") || str.toLowerCase().contains("deadlock") || str.toLowerCase().contains("thread") || str.toLowerCase().contains("slow") || str.toLowerCase().contains("anymor") || str.toLowerCase().contains("memori") || str.toLowerCase().contains("tick") || str.toLowerCase().contains("jvm") || str.toLowerCase().contains("adapt")
                            || str.toLowerCase().contains("deadlock") || str.toLowerCase().contains("sigsegv") || str.toLowerCase().contains("relat") || str.toLowerCase().contains("caus") || str.toLowerCase().contains("snapshot") || str.toLowerCase().contains("segment") || str.toLowerCase().contains("core") || str.toLowerCase().contains("unexpectedli") || str.toLowerCase().contains("build") || str.toLowerCase().contains("loop"))
                    {
                        if (!bugFixCommits.contains(" " + commit + " ")) {
                            bugFixCommits += " " + commit + " ";
                        }
                    }
                }
                prevString = str;
            }
            br.close();
            //System.out.println ("Revisions that were created because of a bug fix = " + bugFixCommits);

        } catch (Exception e) {
            System.out.println("error in getBugFixCommitsLamkanfi = " + e);
        }

        return bugFixCommits;
    }

    public CodeFragment[][] getChangedBugFixCommits() {

        SingleChange[] changedBugFixCommits = new SingleChange[50000];
        SingleChange[][] changedBugFixCommits2D = new SingleChange[5000][5000]; // was 10000 before optimization
        CodeFragment[][] changedBugFixCommits2DNew = new CodeFragment[5000][5000];  // was 10000 before optimization

        try {
            String str = "";

            if (RQ4 == 0)
                str = getBugFixCommits();
            else if (RQ4 == 1)
                str = getBugFixCommitsRQ4();

            String[] bugFixCommits = new String[10000];

            //SingleChange[] changes = new SingleChange[10000];
            SingleChange[] changes = db.getChangedRevisions();

            /*
            for(int j=0; changes[j] != null; j++){
            System.out.println("Revision [" + j + "]= " + changes[j].revision);
            }
             */
            bugFixCommits = str.split("  ");

            /*---------------------------------------- Preprocessing bugFixCommits Start ---------------------------------------------------------------*/
            String[] bugFixCommitsReverse = new String[10000];
            int i = 0;
            for (int j = bugFixCommits.length - 1; j >= 0; j--) {
                bugFixCommitsReverse[i] = bugFixCommits[j];
                //System.out.println("Bug Fix Revision [" + i + "] in Reverse = " + bugFixCommitsReverse[i] + " Where j = " + j);
                i++;
            }
            int len = i;

            //Changing x to x-1 for revision numbers
            for (i = 0; i < len; i++) {
                bugFixCommitsReverse[i] = Integer.toString(Integer.parseInt(bugFixCommitsReverse[i].trim()) - 1);
                //System.out.println("Bug Fix Revision [" + i + "] decreased value by 1 = " + bugFixCommitsReverse[i]);
            }
            /*---------------------------------------- Preprocessing bugFixCommits End ---------------------------------------------------------------*/

            //Matching bug-fix commits with changed revisions and saving in 1D array

            int k = 0;
            for (i = 0; i < len; i++) {
                for (int j = 0; changes[j] != null; j++) {
                    if (bugFixCommitsReverse[i].equals(changes[j].revision)) {
                        changedBugFixCommits[k] = changes[j];
                        //System.out.println("Revision [" + k + "] in changedBugFixCommits = " + changedBugFixCommits[k].revision);
                        k++;
                    }
                }
            }

            //Matching bug-fix commits with changed revisions and saving in 2D array
            int a = 0, b = 0;
            for (i = 0; i < len; i++) {
                for (int j = 0; changes[j] != null; j++) {
                    if (bugFixCommitsReverse[i].equals(changes[j].revision)) {
                        changedBugFixCommits2D[a][b] = changes[j];
                        if(changes[j+1] != null){
                            if(changes[j].revision.equals(changes[j+1].revision)){
                                b++;
                                changedBugFixCommits2D[a][b] = changes[j+1];
                            }
                            else
                                a++;
                        }
                    }
                }
                b = 0;
            }
            // Changing the data type from SingleChange to CodeFragment of the 2D array
            for(i = 0; i<changedBugFixCommits2D.length; i++){
                for(int j = 0; j<changedBugFixCommits2D.length; j++){
                    if(changedBugFixCommits2D[i][j] != null){
                        changedBugFixCommits2DNew[i][j] = new CodeFragment();
                        changedBugFixCommits2DNew[i][j].revision = Integer.parseInt(changedBugFixCommits2D[i][j].revision);
                        changedBugFixCommits2DNew[i][j].startline = Integer.parseInt(changedBugFixCommits2D[i][j].startline);
                        changedBugFixCommits2DNew[i][j].endline = Integer.parseInt(changedBugFixCommits2D[i][j].endline);
                        changedBugFixCommits2DNew[i][j].filepath = changedBugFixCommits2D[i][j].filepath;
                        changedBugFixCommits2DNew[i][j].changetype = changedBugFixCommits2D[i][j].changetype;
                    }
                }
            }

            int count = 0;
            for(i = 0; i<changedBugFixCommits2D.length; i++){
                for(int j = 0; j<changedBugFixCommits2D.length; j++){
                    if(changedBugFixCommits2D[i][j] != null){
                        System.out.println("getChangedBugFixCommits: getChangedBugFixCommits["+i+"]["+j+"].revision = " + changedBugFixCommits2D[i][j].revision
                                + " Filepath = " + changedBugFixCommits2D[i][j].filepath + " Startline = " + changedBugFixCommits2D[i][j].startline
                                + " Endline = " + changedBugFixCommits2D[i][j].endline + " Changetype = " + changedBugFixCommits2D[i][j].changetype);
                        count++;
                    }
                }
            }
            System.out.println("Total number of changed bug-fix code fragments (CF) = " + count);

        } catch (Exception e) {
            System.out.println("error in getChangedBugFixCommits = " + e);
        }
        return changedBugFixCommits2DNew;
    }


    public void bugReplication(){
        try{
            // --------------------------Implementing RQ1----------------------------

            int countFragmentR = 0;
            int countFragmentM = 0;
            int countRevision = 0;

            ArrayList<CodeFragment> bugRepR = new ArrayList<>();
            ArrayList<CodeFragment> bugRepM = new ArrayList<>();


            System.out.println("---------------------------------------Regular Clone Analysis Starts Here--------------------------------------------\n");
            bugRepR = bugReplicationR();
            countFragmentR = bugRepR.size();

            System.out.println("-----------------------------------------Micro Clone Analysis Starts Here--------------------------------------------\n");
            //bugRepM = bugReplicationM();
            countFragmentM = bugRepM.size();

            // Remove all duplicates
            bugRepR.removeAll(bugRepM);

            // Merge two arraylists
            bugRepR.addAll(bugRepM);

            System.out.println("\nThis is the array of replicated bugs after UNION operation in between Regular and Micro: ");
            for(int i=0; i<bugRepR.size(); i++){
                //System.out.println("\nThis is the array of replicated bugs after UNION operation in between Regular and Micro: i = " + i);
                bugRepR.get(i).getFragment();
                bugRepR.get(i).showFragment();
            }

            // Removing the duplicate values based on revisions only
            // Removing duplicate revisions so that we can count the distinct revisions only
            for(int i = 0; i < bugRepR.size(); i++){
                for(int j = i+1; j < bugRepR.size(); j++){

                    if(bugRepR.get(i).revision == bugRepR.get(j).revision){
                        bugRepR.remove(j);
                        j--;
                    }
                }
            }

            System.out.println("\nThis is the array of replicated bugs after UNION operation in between Regular and Micro and after removing duplicate revision number: ");
            for(int i=0; i<bugRepR.size(); i++){
                //System.out.println("\nThis is the array of replicated bugs after UNION operation in between Regular and Micro and after removing duplicate revision number: i = " + i);
                bugRepR.get(i).getFragment();
                bugRepR.get(i).showFragment();
            }

            countRevision = bugRepR.size();

            // Answering RQ1
            // Calculating the average number of replicated bugs for both regular and micro clones

            System.out.println("Total number of distinct clone fragments that experienced bug-replication in Regular clones = " + countFragmentR);
            System.out.println("Total number of distinct clone fragments that experienced bug-replication in Micro clones = " + countFragmentM);
            System.out.println("Total number of distinct revisions that experienced bug-replication in both Regular and Micro clones = " + countRevision);

            System.out.println("Distinct percentage of replicated bugs in regular clones per revision = " + (float) countFragmentR/countRevision);
            System.out.println("Distinct percentage of replicated bugs in micro clones per revision = " + (float) countFragmentM/countRevision);


        } catch(Exception e){
            System.out.println("error in BugReplication = " + e);
            e.printStackTrace();
        }
    }

    public ArrayList<CodeFragment> bugReplicationR(){
        ArrayList<CodeFragment> bugRep = new ArrayList<>();
        try{
            CodeFragment[][] cloneFragmentPair = new CodeFragment[5000][2]; // was 10000 before optimization

            cloneFragmentPair = isClonePair();

            if(cloneFragmentPair != null)
                for (int i = 0; i < cloneFragmentPair.length; i++)
                    for (int j = 0; j < 2; j++)
                        if(cloneFragmentPair[i][j] != null)
                            System.out.println("bugReplicationR: cfp["+i+"]["+j+"].revision = " + cloneFragmentPair[i][j].revision + " Filepath = " + cloneFragmentPair[i][j].filepath
                                    + " Startline = " + cloneFragmentPair[i][j].startline + " Endline = " + cloneFragmentPair[i][j].endline);

            // For RQ3
            int countRevision = 1;
            if(cloneFragmentPair != null)
                for (int i = 0; cloneFragmentPair[i][0] != null; i++)
                    if(cloneFragmentPair[i+1][0] != null)
                        if(cloneFragmentPair[i][0].revision != cloneFragmentPair[i+1][0].revision)
                            countRevision++;


            System.out.println("Total number of distinct bugs(revision) of code clones for Regular = " + countRevision);

            countRevR = countRevision;


            // Finding Replicated Bugs
            int numReplicatedBugFixCommits = 0;
            for(int x = 0; cloneFragmentPair[x][0] != null; x++){
                if(cloneFragmentPair[x][0] != null && cloneFragmentPair[x][1] != null){
                    CodeFragment[] cloneFragmentPairINR = new CodeFragment[2];

                    cloneFragmentPairINR[0] = getInstanceInNextRevision(cloneFragmentPair[x][0]);
                    cloneFragmentPairINR[1] = getInstanceInNextRevision(cloneFragmentPair[x][1]);

                    if(cloneFragmentPairINR[0] != null && cloneFragmentPairINR[1] != null){
                        if(isClonePairBinary(cloneFragmentPairINR[0], cloneFragmentPairINR[1]) == 1){
                            numReplicatedBugFixCommits++;
                            System.out.println("////////////////////////////////////////////////////////////////////////////Replicated Bug Fixing Change Found////////////////////////////////////////////////////////////////////////////");
                            //System.out.println("numReplicatedBugFixCommits for Regular Clones = " + numReplicatedBugFixCommits);

                            bugRep.add(cloneFragmentPair[x][0]);
                            bugRep.add(cloneFragmentPair[x][1]);
                        }
                    }
                }
            }
            System.out.println("Total Number of Pairs of Replicated Bug-Fix Commits for Regular Clones = " + numReplicatedBugFixCommits);

            System.out.println("\nThis is the array of replicated bugs: ");
            for(int i=0; i<bugRep.size(); i++){
                //System.out.println("\nThis is the array of replicated bugs: i = " + i);
                bugRep.get(i).getFragment();
                bugRep.get(i).showFragment();
            }

            // Removing the duplicate values
            for(int i = 0; i < bugRep.size(); i++){
                for(int j = i+1; j < bugRep.size(); j++){

                    if(bugRep.get(i).revision == bugRep.get(j).revision && bugRep.get(i).filepath.equals(bugRep.get(j).filepath)
                            && bugRep.get(i).startline == bugRep.get(j).startline && bugRep.get(i).endline == bugRep.get(j).endline){
                        bugRep.remove(j);
                        j--;
                    }
                }
            }

            System.out.println("\nThis is the array of replicated bugs after removing duplicate objects: ");
            for(int i=0; i<bugRep.size(); i++){
                //System.out.println("\nThis is the array of replicated bugs after removing duplicate objects: i = " + i);
                bugRep.get(i).getFragment();
                bugRep.get(i).showFragment();
            }

            System.out.println("\nTotal distinct number of replicated bugs in regular code clone = " + bugRep.size());

            // Counting the distinct number replicated bug in regular clones per revision
            int countRevisionRep = 1;
            for(int i = 0; i < bugRep.size()-1; i++)
                if(bugRep.get(i).revision != bugRep.get(i+1).revision)
                    countRevisionRep++;

            // For RQ3
            System.out.println("\nTotal distinct number of replicated bug revision in regular code clone = " + countRevisionRep);

            countRevRepR = countRevisionRep;

            float averageCountRegular = (float)bugRep.size()/countRevisionRep;

            System.out.println("\nDistinct number of replicated bugs in regular clones per revision = " + averageCountRegular + "\n");

        }catch(Exception e){
            System.out.println("error in BugReplicationR = " + e);
            e.printStackTrace();
        }
        return bugRep;
    }

}
