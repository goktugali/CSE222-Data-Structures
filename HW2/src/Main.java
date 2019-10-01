public class Main {

    public static void main(String[] args) {

        System.out.println("Driver program. Test informations will be printed with # sign.");
        System.out.println("--------------------------------------------------");
        try {
            ExperimentList list1 = new ExperimentList();

            for (int i = 0; i < 3; i++)
                list1.addExp(new Experiment("exp4_"+i,4,100-i*2.5f,true));
            for (int i = 0; i < 3; i++)
                list1.addExp(new Experiment("exp1_"+i,1,100-i*2.5f,true));
            for (int i = 0; i < 3; i++)
                list1.addExp(new Experiment("exp2_"+i,2,100-i*2.5f,true));
            for (int i = 0; i < 3; i++)
                list1.addExp(new Experiment("exp3_"+i,3,100-i*2.5f,true));

            System.out.println("# Test 1 : addExp() method tested.");
            System.out.println("adding order for days of experiments is : 4,1,2,3.\nSo, all adding posibilites are tested.");
            System.out.println("Printing the list :");
            list1.printList();
            System.out.println("# Test 1 Finished\n---------------------------");
            for (int i = 1; i <= 4; i++)
                list1.orderDay(i);
            System.out.println("# Test 2 : orderDay() method tested.\norderDay(1),orderDay(2),orderDay(3),orderDay(4) executed.");
            System.out.println("Printing the list :");
            list1.printList();
            System.out.println("# Test 2 Finished\n---------------------------");
            list1.removeExp(1,0);
            list1.removeExp(3,0);
            list1.removeExp(2,0);
            list1.removeExp(4,0);
            System.out.println("# Test 3 : removeExp() method tested.\nremoveExp(1,0),removeExp(2,0),removeExp(3,0),removeExp(4,0) executed.");
            System.out.println("Printing the list :");
            list1.printList();
            System.out.println("Experiments removed succesfully.\n# Test 3 Finished\n---------------------------");
            list1.removeDay(2);
            list1.removeDay(4);
            System.out.println("# Test 4 : removeDay() method tested.\nremoveDay(2), removeDay(4) executed.");
            System.out.println("Printing the list :");
            list1.printList();
            System.out.println("Days removed succesfully.\n# Test 4 Finished\n---------------------------");
            list1.setExp(1,1,new Experiment("new_exp1",1,15f,false));
            list1.setExp(3,0,new Experiment("new_exp2",3,35f,false));
            System.out.println("# Test 5 : setExp() method tested. setExp(1,1), setExp(3,0)\nexecuted with experiment 15 and 35 accuracy values.");
            System.out.println("For testing purposes of listExp() method which are will be tested next step,completed selected false.");
            System.out.println("Printing the list :");
            list1.printList();
            System.out.println("Experiments setted successfully.\n# Test 5 finished\n---------------------------");
            System.out.println("# Test 6 : listExp() method tested.\nlistExp(1), listExp(3) executed.\n---------------------------");
            list1.listExp(1);
            list1.listExp(3);
            System.out.println("Completed experiments succesfully printed.\n# Test 6 finished.\n---------------------------");
            System.out.println("# Test 7 : orderExperiment() method tested via printOrderedExp method.\nScroll up and see list before ordered in Test 5.");
            System.out.println("---------------------------");
            list1.printOrderedExp();
            System.out.println("---------------------------");
            System.out.println("# Experiments are ordered succesfully.\n# Test 7 finished.\n---------------------------");
            System.out.println("# Test 8 : getExp() method tested. getExp(3,0) invoked.\nScroll up and see in Test 5.");
            System.out.println("getExp(3,0) returns : " + list1.getExp(3,0));
            System.out.println("# Test 8 finished. Experiment printed succesfully\n---------------------------\n");
            System.out.println("While printing the list to the screen, (in printList method), for-each loop used.");
            System.out.println("ExperimentList is iterable, tested succesfully.\n---------------------------");

        }
        catch (Exception ex)
        {
            System.out.println("Error occured : "+ex.getMessage());
        }
        finally {
            System.out.println("Driver program execution is finished.");
        }

    }
}
