import mpi.*;

public class DiffusionCentralisee {
    public static void main(String args[]) throws Exception {
	MPI.Init(args);
	int me = MPI.COMM_WORLD.Rank();
	int size = MPI.COMM_WORLD.Size();
	String bufferString[] = new String[1];

	int idBroadCaster = 0;
	if(args[3].equals("-broadcaster")){
		idBroadCaster = Integer.parseInt(args[4]);
	}

	if(me == idBroadCaster){
	    bufferString[0] = "coucou";
	    System.out.println("I'm <"+me+">: send " + bufferString[0]);
	    for(int i=0; i<size; i++){
	    	if(i != me){
				MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, i, 99);
			}
	    }
	}else{
	    Status mps = MPI.COMM_WORLD.Recv( bufferString, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, 99 );
	    System.out.println("I'm <"+me+">: receive " + bufferString[0]);
	}
	MPI.Finalize();
    }
}
