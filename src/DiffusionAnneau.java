import mpi.MPI;
import mpi.Status;

public class DiffusionAnneau {
    public static void main(String args[]) throws Exception {
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int idBroadCaster=0;
        String bufferString[] = new String[1];

        if(me == idBroadCaster){
            bufferString[0] = "coucou";
            System.out.println("I'm <"+me+">: send " + bufferString[0]);
            MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, me +1, 99);
        } else {
            Status mps = MPI.COMM_WORLD.Recv( bufferString, 0, 1, MPI.OBJECT,MPI.ANY_SOURCE , 99 );
            System.out.println("I'm <"+me+">: receive " + bufferString[0]);
            if(me < size -1){
                MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, me +1, 99);
                System.out.println("I'm <"+me+">: send " + bufferString[0]);
            }
        }
        MPI.Finalize();
    }
}