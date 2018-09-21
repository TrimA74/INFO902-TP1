import mpi.MPI;
import mpi.Status;

public class DiffusionAnneau {
    public static void main(String args[]) throws Exception {
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int idBroadCaster = 0;
        if(args[3].equals("-broadcaster")){
            idBroadCaster = Integer.parseInt(args[4]);
        }

        String bufferString[] = new String[1];

        if(me == idBroadCaster){
            bufferString[0] = "coucou";
            System.out.println("I'm <"+me+">: send " + bufferString[0] + " to " + (me +1 % size));
            MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, (me +1) % size, 99);
        } else {
            Status mps = MPI.COMM_WORLD.Recv( bufferString, 0, 1, MPI.OBJECT,MPI.ANY_SOURCE , 99 );
            System.out.println("I'm <"+me+">: receive " + bufferString[0]);
            if(me != (idBroadCaster -1) % size){
                MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, (me +1) % size, 99);
                System.out.println("I'm <"+me+">: send " + bufferString[0]);
            }
        }
        MPI.Finalize();
    }
}
