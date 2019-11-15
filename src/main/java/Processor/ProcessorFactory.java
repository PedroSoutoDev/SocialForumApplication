package Processor;

public class ProcessorFactory {
    public static Processor makeProcessor(String request) {
        switch(request) {
            case "/posts":
                //TODO
                return new PostProcessor();
            case "/users":
                //TODO
                return new UserProcessor();
            default:
                //TODO
                return new BadRequestProcessor();
        }
    }
}
