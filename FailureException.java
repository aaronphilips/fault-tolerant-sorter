class FailureException extends RuntimeException
{
      public FailureException() {

      }

      public FailureException(String message)
      {
         super(message);
      }
}
