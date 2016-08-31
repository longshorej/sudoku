package info.longshore.sudoku.domain;

import java.awt.Graphics2D;
import org.sourceforge.jlibeps.epsgraphics.EpsGraphics2D;

/**
 * EPS-specific graphics2D code.
 */
public class EPSGraphics2DFilePublisher extends Graphics2DFilePublisher
{
    public EPSGraphics2DFilePublisher()
    {
        graphics2D = new EpsGraphics2D();
    }

    @Override
    public void finalize()
    {
        if (graphics2D != null) {
            graphics2D.dispose();
        }
    }

    @Override
    public byte[] getByteRepresentation()
    {
        byte[] byteArray = graphics2D.toString().getBytes();
        reset();
        return byteArray;
    }

    @Override
    public String getFormatName()
    {
        return "EPS";
    }

    @Override
    public String getExtensionName()
    {
        return "eps";
    }

    @Override
    public Graphics2D getGraphics2D()
    {
        return graphics2D;
    }

    private void reset()
    {
        graphics2D = new EpsGraphics2D();
    }
    
    EpsGraphics2D graphics2D;
}
