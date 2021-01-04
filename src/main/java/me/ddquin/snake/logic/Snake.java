package me.ddquin.snake.logic;

import me.ddquin.snake.logic.Cell;
import me.ddquin.snake.logic.Direction;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<SnakeCell> body;
    private Direction curDirection;

    public Snake(Cell head) {
        body = new ArrayList<>();
        SnakeCell headToAdd = new SnakeCell(head.getX(), head.getY());
        headToAdd.setCellDirection(Direction.RIGHT);
        this.curDirection = Direction.RIGHT;
        body.add(headToAdd);
    }

    public void grow() {
        SnakeCell tailCell = body.get(body.size() - 1);
        body.add(tailCell.getNewSnakeCell());
    }

    public void setHeadDirection(Direction newDir) {
        //This stops snake going to the opposite direction of where it is already going eg. snake can't go up if going down and vice versa with same for right and left
        if (newDir == Direction.getOpposite(body.get(0).getCellDirection())) return;
        curDirection = newDir;
    }

    public void moveStep() {
        //First step of this method, update the direction of all cells
        for (int i = body.size() - 1; i >= 0; i--) {
            SnakeCell curCell = body.get(i);
            //For the case of the head there is no next cell so update its direction to the current user inputted direction
            if (i == 0) {
                curCell.setCellDirection(curDirection);
            } else {
                SnakeCell nextCell = body.get(i - 1);
                curCell.setCellDirection(nextCell.getCellDirection());
            }
        }
        //Now after direction is updated move all cells one place in their direction
        body.forEach(snakeCell -> snakeCell.moveCellInDirection());
    }

    public Cell getHead() {
        return body.get(0);
    }

    public List<Cell> getBody() {
        List<Cell> bodyWithoutHead = new ArrayList<>();
        for (int i = 1; i < body.size(); i++) {
            bodyWithoutHead.add(body.get(i).getCell());
        }
        return bodyWithoutHead;
    }

    public Cell getTail() {
        return body.get(body.size() - 1);
    }

    public Direction getCellDirection(Cell cell) {
        for (SnakeCell sCell: body) {
            if (sCell.getCell().equals(cell)) {
                return sCell.getCellDirection();
            }
        }
        return null;
    }


    public boolean isSnakeCell(Cell c) {
        boolean isSnakeCell = false;
        for (SnakeCell sCell: body) {
            if (sCell.getCell().equals(c)) {
                isSnakeCell = true;
            }
        }
        return isSnakeCell;
    }

    public int getSize() {
        return body.size();
    }

    private class SnakeCell extends Cell{

        private Direction cellDirection;

        public SnakeCell(int x, int y) {
            super(x, y);
        }

        public void setCellDirection(Direction cellDirection) {
            this.cellDirection = cellDirection;
        }

        public Cell getCell() {
            return new Cell(super.getX(), super.getY());
        }

        public void moveCellInDirection() {
            if (cellDirection == Direction.RIGHT) this.setX(this.getX() + 1);
            if (cellDirection == Direction.LEFT) this.setX(this.getX() - 1);
            if (cellDirection == Direction.UP) this.setY(this.getY() - 1);
            if (cellDirection == Direction.DOWN) this.setY(this.getY() + 1);
        }

        public SnakeCell getNewSnakeCell() {
            SnakeCell cellNew = null;
            if (cellDirection == Direction.RIGHT) cellNew = new SnakeCell(this.getX() - 1, this.getY());
            if (cellDirection == Direction.LEFT) cellNew = new SnakeCell(this.getX() + 1, this.getY());
            if (cellDirection == Direction.UP) cellNew = new SnakeCell(this.getX(), this.getY() + 1);
            if (cellDirection == Direction.DOWN) cellNew = new SnakeCell(this.getX(), this.getY() - 1);
            cellNew.setCellDirection(cellDirection);
            return cellNew;
        }

        public Direction getCellDirection() {
            return cellDirection;
        }
    }


}
