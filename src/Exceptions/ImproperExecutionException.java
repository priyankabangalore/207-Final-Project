package Exceptions;

/**
 * An exception thrown when a command is thrown in an improper location.
 */
public class ImproperExecutionException extends RuntimeException {

    private final String properExecutionLocation;
    private final String executionLocation;

    /**
     * Wrong execution exception
     * @param properExecutionLocation The correct execution location
     * @param executionLocation Where the execution is located
     */
    public ImproperExecutionException(String properExecutionLocation, String executionLocation){
        this.properExecutionLocation = properExecutionLocation;
        this.executionLocation = executionLocation;
    }

    /**
     * String rep of improper execution exception.
     * @return String detailing the improper execution and where it should be executed.
     */
    @Override
    public String toString(){
        return "This command is supposed to be executed in " + properExecutionLocation +
                " but was executed in" + executionLocation;
    }
}

