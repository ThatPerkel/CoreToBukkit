package org.ships.implementation.bukkit.utils;

import org.bukkit.block.BlockFace;
import org.core.world.direction.Direction;
import org.core.world.direction.SixteenFacingDirection;

public abstract class DirectionUtils {

    public static org.bukkit.block.BlockFace toFace(Direction direction){
        if(direction.equals(SixteenFacingDirection.NORTH)){
            return BlockFace.NORTH;
        }else if(direction.equals(SixteenFacingDirection.EAST)){
            return BlockFace.EAST;
        }else if(direction.equals(SixteenFacingDirection.SOUTH)){
            return BlockFace.SOUTH;
        }else if(direction.equals(SixteenFacingDirection.WEST)){
            return BlockFace.WEST;
        }else if(direction.equals(SixteenFacingDirection.NORTH_EAST)){
            return BlockFace.NORTH_EAST;
        }
        //TODO
        else if(direction.equals(SixteenFacingDirection.UP)){
            return BlockFace.UP;
        }else if(direction.equals(SixteenFacingDirection.DOWN)){
            return BlockFace.DOWN;
        }
        return null;
    }

    public static Direction toDirection(org.bukkit.block.BlockFace face){
        switch(face){
            case NORTH:
                return SixteenFacingDirection.NORTH;
            case EAST:
                return SixteenFacingDirection.EAST;
            case SOUTH:
                return SixteenFacingDirection.SOUTH;
            case WEST:
                return SixteenFacingDirection.WEST;
            case UP:
                return SixteenFacingDirection.UP;
            case DOWN:
                return SixteenFacingDirection.DOWN;
            case NORTH_EAST:
                return SixteenFacingDirection.NORTH_EAST;
            case NORTH_WEST:
                return SixteenFacingDirection.NORTH_WEST;
            case SOUTH_EAST:
                return SixteenFacingDirection.SOUTH_EAST;
            case SOUTH_WEST:
                return SixteenFacingDirection.SOUTH_WEST;
            case WEST_NORTH_WEST:
                break;
            case NORTH_NORTH_WEST:
                return SixteenFacingDirection.NORTH_NORTH_EAST;
            case NORTH_NORTH_EAST:
                break;
            case EAST_NORTH_EAST:
                break;
            case EAST_SOUTH_EAST:
                break;
            case SOUTH_SOUTH_EAST:
                break;
            case SOUTH_SOUTH_WEST:
                break;
            case WEST_SOUTH_WEST:
                break;
            case SELF:
                break;
        }
        return null;
    }
}
