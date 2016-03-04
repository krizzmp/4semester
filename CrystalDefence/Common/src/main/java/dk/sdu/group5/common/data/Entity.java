package dk.sdu.group5.common.data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Entity
{
    private final UUID ID = UUID.randomUUID();
    private int x, y;
    private final Set<String> properties;
    private EntityType type;

    public Entity()
    {
        properties = new HashSet<>();
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public EntityType getType()
    {
        return type;
    }

    public void setType(EntityType type)
    {
        this.type = type;
    }

    public void addProperty(String property) throws Exception
    {
        // TO-BE-RESOLVED: Silent handling, throw exception or return boolean
        // if duplicate found.
        if (!properties.add(property))
        {
            throw new Exception("A duplicate entry already exists matching the provided property!");
        }
    }

    public void removeProperty(String property) throws Exception
    {
        // TO-BE-RESOLVED: Silent handling, throw exception or return boolean
        // if duplicate found.
        if (!properties.remove(property))
        {
            throw new Exception("No entry already exists matching the provided property!");
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.ID);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Entity other = (Entity) obj;
        if (!Objects.equals(this.ID, other.ID))
        {
            return false;
        }
        if (this.x != other.x)
        {
            return false;
        }
        if (this.y != other.y)
        {
            return false;
        }
        if (!Objects.equals(this.properties, other.properties))
        {
            return false;
        }
        return true;
    }
}
