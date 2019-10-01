import java.util.NoSuchElementException;
/**
 * ExperimentList class.
 * @author Goktug Akin,161044018
 * @version HW2
 * @since 2019
 */
public class ExperimentList implements Iterable<Experiment>{

    private Node head;
    private int size=0;

    private static class Node{

        private Experiment data;
        private Node next=null;
        private Node nextDay=null;

        private Node(Experiment ex)
        {
            data = ex;
        }
    }
    /* PRIVATE HELPER METHODS */

    /**
     * Helper method.
     * Counts number of days in the list
     * @param day day value which will be countered
     * @return number of occurences of given day.
     */
    private int countDay(int day)
    {
        int dayctr=0;
        ExpListIterator iter = this.iterator();
        while(iter.hasNext())
            if(iter.next().getDay()==day)
                dayctr++;
        return dayctr;
    }

    /**
     * Helper method.
     * Check whether day value is in the list or not.
     * @param day Day value which will be checked.
     * @return true if list contains given day value.
     */
    private boolean containsDay(int day)
    {
        for(Experiment e : this)
            if(e.getDay()==day)
                return true;
        return false;
    }

    /**
     * Helper method.
     * Finds the node by given informations.
     * @param day day value which whill be searched in the list.
     * @param index index of day of experiment.
     * @return Node.
     */
    private Node findNode(int day,int index)
    {
        ExpListIterator iter = this.iterator();
        int dayCounter = 0;
        Node tmpNode = null;
        while(iter.hasNext())
        {
            tmpNode = iter.nextNode();
            if(iter.next().getDay()==day)
                ++dayCounter;
            if(dayCounter-1==index)
                break;
        }
        return tmpNode;
    }

    /**
     * Helper method.
     * Finds the by the given First node.
     * @param node First of node which will be searched.
     * @return
     */
    private Node findLastNode(Node node)
    {
        ExpListIterator iter = new ExpListIterator(node);
        Node tmpNode;
        Node returnNode = null;

        while (iter.hasNext())
        {
            tmpNode = iter.nextNode();
            if (iter.next().getDay() == node.data.getDay())
                returnNode = tmpNode;
        }
        return returnNode;
    }

    /**
     * Checks given day is a new day or not.
     * @param day Day value that which will be checked.
     * @return true if day value is new day in the list.
     */
    private boolean checkNewDay(int day)
    {
        return !containsDay(day);
    }
    /* PRIVATE HELPER METHODS END */

    /**
     * Creates an empty list.
     */
    public ExperimentList()
    {}
    /**
     * Prints the list to the screen.
     */
    public void printList()
    {
        System.out.println("---------------------------");
        System.out.println("# ExperimentList | Size : " + this.getSize());
        System.out.println("---------------------------");
        int maxDay=head.data.getDay();
        for(Experiment e : this) {
            if(e.getDay()>maxDay) {
                System.out.println();
                maxDay=e.getDay();
            }
            System.out.println(e);
        }
        System.out.println("---------------------------");
        this.printDays();
    }
    private void printDays()
    {
        Node last = head;
        System.out.println("# List day view:");
        last = head;
        while( last != null) {
            System.out.println(last.data);
            last = last.nextDay;
        }
        System.out.println("---------------------------\n");
    }
    /**
     * Prints ordered (according to accuracy) (ascending sort) list.
     */
    public void printOrderedExp()
    {
        ExpListIterator iter = new ExpListIterator(orderExperiments());
        while(iter.hasNext())
            System.out.println(iter.next());
    }
    /**
     * Adds an experiment to the list
     * @param e Experiment that will be added to the list
     */
    public void addExp(Experiment e)
    {
        /*Adding to the head of the list*/
        if(size==0){
            head = new Node(e);
        }
        else if(checkNewDay(e.getDay())){
            /*If head of the list, greater than new Experiment*/
            if(head.data.getDay()>e.getDay()){
                Node newNode = new Node(e);
                newNode.next=head;
                newNode.nextDay=head;
                head=newNode;
            }
            else {
                Node newNode = new Node(e);
                ExpListIterator iter = this.iterator();
                Node prevNode = iter.nextNode();
                /* In order to speed up to deleting process, newDay structure used here*
                   By this for loop, we can easily jump to next days.
                 */
                while(iter.hasNextDay() && e.getDay()>iter.nextNode().data.getDay())
                {
                    prevNode=iter.nextNode();
                    iter.nextDay();
                }
                Node lastNode;
                /*adding the end of list. ex : adding day3 to the this list : 1-2-2 */
                if(e.getDay()>iter.nextNode().data.getDay()) {
                    lastNode=findLastNode(iter.nextNode());
                    iter.nextNode().nextDay=newNode;
                    lastNode.next=newNode;
                }
                /*Adding to the between 2 days. ex : adding day2 to the this list : 1-3-3 */
                else {
                    lastNode=findLastNode(prevNode);
                    newNode.next=lastNode.next;
                    newNode.nextDay=prevNode.nextDay;
                    lastNode.next=newNode;
                    prevNode.nextDay=newNode;
                }
            }
        }
        /*If new day, is not a new day, such as adding 2 to the this list : 1-2-2..*/
        else
        {
            Node tempNode = findLastNode(findNode(e.getDay(),0));
            Node newItem = new Node(e);
            newItem.next = tempNode.next;
            tempNode.next = newItem;
        }
        ++size;
    }

    /**
     * Returns the experiment with given parameters.
     * @param day Day value of experiment that will be returned.
     * @param index Index value of experiment that will be returned.
     * @return Experiment with given day and index.
     * @throws IndexOutOfBoundsException If index is illegal (index&lt;0 || index&gt;=number of day)
     * @throws  NoSuchElementException If there is no experiment in the list given day.
     */
    public Experiment getExp(int day, int index)
    {
        int dayNum = this.countDay(day);
        if(dayNum==0 )
            throw new NoSuchElementException("No experiment founded");
        if(index<0 || index>=dayNum)
            throw new IndexOutOfBoundsException("Illeagal index " + index);

        return findNode(day,index).data;
    }
    /**
     * Returns the size of list.
     * @return the size of list.
     * @throws NoSuchElementException If there is no experiment in the list given day.
     * @throws IndexOutOfBoundsException If index is illegal (index&lt;0 || inde&gt;=umber of day).
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Changes the experiment within given day and index parameters.
     * @param day Day value of experiment that will be setted.
     * @param index Index value of experiment that will be setted.
     * @param e New experiment that will be assigned.
     * @throws NoSuchElementException If there is no experiment in the list given day.
     * @throws IndexOutOfBoundsException If index is illegal (index&lt;0 || index&gt;=number of day).
     */
    public void setExp(int day,int index,Experiment e)
    {
        int dayNum = this.countDay(day);
        if(day!=e.getDay())
            throw new IllegalArgumentException();
        if(dayNum==0)
            throw new NoSuchElementException("No experiment founded");
        if(index<0 || index >=dayNum)
            throw new IndexOutOfBoundsException("Illeagal index " + index);

        findNode(day,index).data = e;
    }

    /**
     * Remove the experiment specified as index from given day
     * @param day Day value of experiment that will be removed.
     * @param index Index value of experiment that will be removed.
     * @throws NoSuchElementException If there is no experiment in the list given day.
    *  @throws IndexOutOfBoundsException If index is illegal (index&lt;0 || index&gt;=number of day).
     * @return true If experiment is succesfully removed.
     */
    public boolean removeExp(int day,int index)
    {
        int dayNum = this.countDay(day);
        if(dayNum==0)
            throw new NoSuchElementException("No experiment founded");
        if(index<0 || index >=dayNum)
            throw new IndexOutOfBoundsException("Illeagal index " + index);

        ExpListIterator iter = this.iterator();
        Node prevNode=null;
        while(iter.hasNextDay())
        {/* In order to speed up to deleting process, newDay structure used here*/
            prevNode = iter.nextNode();
            if (prevNode.data.getDay() == day) {
                break;
            }
            iter.nextDay();
        }
        /*continue to index'th element*/
        for (int i = 0; i < index ; i++)
            iter.next();

        iter.remove();
        return true;
    }
    /**
     * Prints all the completed experiments by the given day.
     * @param day Day value of experiments that will be printed.
     */
    public void listExp(int day)
    {
       for(Experiment ex : this)
           if(ex.getDay()==day && ex.isCompleted())
               System.out.println(ex);
    }

    /**
     * Remove the experiment specified as day
     * @param day Day value of experiments that will be removed.
     * @return true If experiments are successfully removed.
     */
    public boolean removeDay(int day)
    {
        int numOfDays = countDay(day);
        if(numOfDays==0)
            return false;
        for (int i = 0; i <numOfDays ; i++)
            this.removeExp(day,0);/*remove all days by other remove method (every step, 0th index deleted)*/
        return true;
    }

    /**
     * Sorts the experiments in a given day according to the accuracy, the
     * changes will be done on the list
     * @param day Day value of experiments that will be sorted.
     */
    public void orderDay(int day)
    {
        if(day<=0 || !containsDay(day))
            throw new IllegalArgumentException("day is invalid");
        Node startingNode = findNode(day,0);
        Node tmpNode=null;
        ExpListIterator iter = this.iterator();
        while(iter.hasNext())
        {
            tmpNode=iter.nextItem;
            iter.nextItem=iter.nextItem.next;
            if(iter.nextItem==startingNode)
                break;
        }
        /* Backup(temp) Nodes */
        Node startBack = tmpNode;
        Node finishNode = findLastNode(startingNode);
        Node nextDayBackup = startingNode.nextDay;
        Node finishNext = finishNode.next;
        /* Backup Nodes */
        for (Node node1 = startingNode; node1 != finishNode.next; node1 = node1.next)
        {
            Node min = node1;
            for (Node node2 = node1; node2 != finishNode.next; node2 = node2.next)
                if (min.data.getAccuracy() > node2.data.getAccuracy())
                    min = node2;

            Node temp = new Node(node1.data);
            node1.data = min.data;
            min.data = temp.data;
        }
        if(startBack==null)
            head=startingNode;
        else {
            startBack.nextDay = startingNode;
        }
        finishNode.nextDay = finishNext;
        startingNode.nextDay = nextDayBackup;
    }

    /**
     * Sorts the list (ascending sort) and return new sorted list.
     * @return New sorted list.
     */
    public Node orderExperiments()
    {
        ExperimentList newList = new ExperimentList();/* create new list*/
        for(Experiment ex : this)
            newList.addExp(new Experiment(ex));

        /*Sorting the list. O(n'2)*/
        for (Node node1 = newList.head; node1 !=null; node1 = node1.next)
        {
            Node min = node1;
            for (Node node2 = node1; node2 != null; node2 = node2.next)
                if (min.data.getAccuracy() > node2.data.getAccuracy())
                    min = node2;

            Node temp = new Node(node1.data);
            node1.data = min.data;
            min.data = temp.data;
        }
        ExpListIterator iter = newList.iterator();
        while(iter.hasNext()){
            iter.nextItem.nextDay=null;
            iter.next();
        }
        return newList.head;
    }

    @Override
    public String toString()
    {
        return String.format("# ExperimentList | Size : " + this.getSize());
    }

    /**
     * Returns an iterator over the ExperimentList.
     * @return Iterator over the ExperimentList.
     */
    @Override
    public ExpListIterator iterator()
    {
        return new ExpListIterator(0);
    }
    private class ExpListIterator implements ExperimentListIterator
    {
        private Node lastItemReturned = null;
        private Node nextItem;

        /**
         * returns the next node that points by iterator.
         * @return next item.
         */
        private Node nextNode()
        {
            return nextItem;
        }

        /**
         * Constructor that provides iterator to point for specific node
         * @param node node that iterator will be point.
         */
        private ExpListIterator(Node node)
        {
            nextItem=node;
        }

        /**
         * Constructor that provides iterator to point specific index.
         * @param index index'th node that iterator will be point.
         */
        private ExpListIterator(int index)
        {
            if(index < 0 || index>size)
                throw new IndexOutOfBoundsException("Invalid index" + index);
            if(index==size)
                nextItem=null;
            else{
                nextItem=head;
                for (int i = 0; i < index; i++)
                    nextItem=nextItem.next;
            }
        }

        /**
         * Checks nextNode has a nextDay or not. This method implemented to speed up add and remove methods.
         * @return true if nextItem has a nextDay.
         */
        private boolean hasNextDay()
        {
            return nextItem.nextDay!=null;
        }

        /**
         * Checks nextNode has a nextDay or not. This method implemented to speed up add and remove methods.
         * Jumps to nextDay.
         * @return nextDay of current node.
         */
        private Experiment nextDay()
        {
            if(!hasNext())
                throw new NoSuchElementException();

            lastItemReturned=nextItem;
            nextItem=nextItem.nextDay;
            return lastItemReturned.data;
        }
        /**
         * Returns true if the iteration has more elements.
         * @return true if the iteration has more elements
         */
        @Override
        public boolean hasNext()
        {
            return nextItem!=null;
        }
        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Experiment next()
        {
            if(!hasNext())
                throw new NoSuchElementException();

            lastItemReturned=nextItem;
            nextItem=nextItem.next;
            return lastItemReturned.data;
        }
        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.
         * @throws IllegalStateException if the remove operation is not supported by this iterator.(Calling remove() before calling next())
         */
        @Override
        public void remove()
        {
            if(nextItem==head)
            {
                if(head.next!=null)
                    if(head.next.data.getDay()==head.data.getDay())
                        head.next.nextDay=head.nextDay;
                head=head.next;
                --size;
                return;
            }

            if(lastItemReturned==null)
                throw new IllegalStateException();

            Node lastRef = lastItemReturned;
            while(lastRef.next.data.getDay()<nextItem.data.getDay())
                lastRef=lastRef.next;
            lastRef.next=nextItem.next;

            if(nextItem.next!=null) {
                if (nextItem.next.data.getDay() > nextItem.data.getDay())
                    lastItemReturned.nextDay = nextItem.nextDay;
                else {
                    nextItem.next.nextDay = nextItem.nextDay;
                    lastItemReturned.nextDay=nextItem.next;
                }
            }
            else
                lastItemReturned.nextDay=null;
            size--;
        }
    }
}