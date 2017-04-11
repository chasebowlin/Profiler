public class ProfilerException {

	public ProfilerException() {
		Thread.dumpStack();
	}

}