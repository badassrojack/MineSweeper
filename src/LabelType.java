/**
 * This enum class shows 3 types of label on the minesweeper panel, it is used to help button on the label to
 * determine its operation depend on the type of label underneath.
 */

public enum LabelType {
    EMPTY{
        @Override
        public String toString(){
            return"Empty Label";
        }
    }, BOMB{
        @Override
        public String toString(){
            return"Bomb";
        }
    }, NUMBER{
        @Override
        public String toString(){
            return"Number Label";
        }
    };
}
