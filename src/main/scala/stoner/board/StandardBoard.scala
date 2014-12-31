 package stoner.board

 import scala.collection.GenTraversableOnce

class StandardBoard(val grid : Grid = new CompactGrid) extends Board {
   
   def setStones(posManipulationSeq : GenTraversableOnce[PosFlip]) : Board = {
     //new StandardBoard(posManipulationSeq.foldLeft[Grid](grid)((g: Grid, pf : PosFlip) => pf + g)
     new StandardBoard((grid /: posManipulationSeq)((g, pf) => pf + g))
     
     
   }//end def setStone
   
  //FIXME
  override def +(move : Move) : Board = this
    
}

//31337