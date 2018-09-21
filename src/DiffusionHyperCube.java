import mpi.MPI;
import mpi.Status;

public class DiffusionHyperCube {
    public static void main(String args[]) throws Exception {
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int idBroadCaster = 0;
        Object buffer[] = new Object[2];

        if(me == idBroadCaster){
            buffer[0] = "coucou";
            buffer[1] = 0;
            System.out.println("I'm <"+me+">: send " + buffer[0] + " to " + ((me+1) % size));
            MPI.COMM_WORLD.Send(buffer, 0, 1, MPI.OBJECT, (me +1) % size , 99);
        }else{
            Status mps = MPI.COMM_WORLD.Recv( buffer, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, 99 );
            System.out.println("I'm <"+me+">: receive " + buffer[0] + " state" + buffer[1]);
            if(me +1 <= size/2){
                System.out.println("I'm <"+me+">: send " + buffer[0] + " to " + (me +1) );
                MPI.COMM_WORLD.Send(buffer, 0, 1, MPI.OBJECT, me +1, 99);
            }
        } /*
        else {
            Status mps = MPI.COMM_WORLD.Recv( buffer, 0, 1, MPI.OBJECT,MPI.ANY_SOURCE , 99 );
            System.out.println("I'm <"+me+">: receive " + buffer[0]);
            if(me > size/2){
                System.out.println("I'm <"+me+">: send " + buffer[0] + " to " + (((me+1) %size) -1) );
                MPI.COMM_WORLD.Send(buffer, 0, 1, MPI.OBJECT, ((me+1) %size) -1, 99);
            }
        }*/
        MPI.Finalize();
    }
}
