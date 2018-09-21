import mpi.*;

public class DiffusionCentralisee {
    public static void main(String args[]) throws Exception {
	MPI.Init(args);
	int me = MPI.COMM_WORLD.Rank();
	int size = MPI.COMM_WORLD.Size();
	String bufferString[] = new String[1];

	if(me == 0){
	    bufferString[0] = "coucou";
	    System.out.println("I'm <"+me+">: send " + bufferString[0]);
	    for(int i=1; i<size; i++){
			MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, i, 99);
	    }
	}else{
	    Status mps = MPI.COMM_WORLD.Recv( bufferString, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, 99 );
	    System.out.println("I'm <"+me+">: receive " + bufferString[0]);
	}
	MPI.Finalize();
    }
}
