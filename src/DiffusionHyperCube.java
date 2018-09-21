import mpi.MPI;
import mpi.Status;

public class DiffusionHyperCube {
    public static void main(String args[]) throws Exception {
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int idBroadCaster = 0;
        if(args[3].equals("-broadcaster")){
            idBroadCaster = Integer.parseInt(args[4]);
        }
        // init buffer
        String buffer[] = new String[2];
        if(me == idBroadCaster){
            buffer[0] = "coucou";
            buffer[1] = "0";
            System.out.println("I'm <"+me+">: send " + buffer[0] + " to " + (me +1 % size));
            MPI.COMM_WORLD.Send(buffer, 0, 2, MPI.OBJECT, me + 1 % size, 99);

        } else {
            Status mps = MPI.COMM_WORLD.Recv( buffer, 0, 2, MPI.OBJECT, MPI.ANY_SOURCE, 99 );
            System.out.println("I'm <"+me+">: receive " + buffer[0] + " state" + buffer[1]);
        }
        double state = Double.parseDouble(buffer[1]);
        while(Math.pow(2,state +1 ) -1 < size){
            state++;
            buffer[1]=Double.toString(state);
            if(( (me - idBroadCaster + size) % size ) + Math.pow(2,state) < size){
                System.out.println("I'm <"+me+">: send " + buffer[0] + " to " + (int) (me + Math.pow(2,state)) % size);
                MPI.COMM_WORLD.Send(buffer, 0, 2, MPI.OBJECT, (int) (me + Math.pow(2,state)) % size , 99);
            }

        }
        MPI.Finalize();
    }
}
