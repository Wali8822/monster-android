package com.onesoft.monsters;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Simulation implements ContactListener{
	protected World          m_theWorld = null;
	
	protected Body           m_theLeft = null;
	protected Body           m_theRight = null;
	protected Body           m_theTop = null;
	protected Body           m_theBottom = null;
	
	protected float          m_timeStep = 0.0f;
	protected int            m_velocityIterations = 0;
	protected int            m_positionIterations = 0;
	
	public Simulation(){
		Vector2 gravity = new Vector2(0,0.0f);
		m_theWorld = new World(gravity,true);
		m_theWorld.setContactListener(this);
		
		//create the ground
		BodyDef bd = new BodyDef();
		bd.position.set(Settings.GROUND_X, Settings.GROUND_Y);
		bd.type = BodyType.StaticBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsEdge(new Vector2(0, 0), 
				new Vector2(Settings.SCREEN_WIDTH, 0));
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 0.0f;
		fd.filter.categoryBits = Settings.WALL_GROUP;
		m_theBottom = addBody(bd,fd);
		
		bd.position.set(0.0f, Settings.GROUND_Y);
		bd.type = BodyType.StaticBody;
		shape.setAsEdge(new Vector2(0.0f,0.0f),
				new Vector2(0.0f,Settings.SCREEN_HEIGHT - Settings.GROUND_Y));
		fd.shape = shape;
		m_theLeft = addBody(bd, fd);
		
		bd.position.set(Settings.SCREEN_WIDTH, Settings.GROUND_Y);
		bd.type = BodyType.StaticBody;
		shape.setAsEdge(new Vector2(0.0f,0.0f),
				new Vector2(0.0f,Settings.SCREEN_HEIGHT - Settings.GROUND_Y));
		fd.shape = shape;
		m_theRight = addBody(bd, fd);
		
		bd.position.set(0.0f, Settings.SCREEN_HEIGHT);
		bd.type = BodyType.StaticBody;
		shape.setAsEdge(new Vector2(0.0f,0.0f), 
				new Vector2(Settings.SCREEN_WIDTH,0.0f));
		fd.shape = shape;
		m_theTop = addBody(bd, fd);
		
		m_timeStep = 1.0f/60.0f;
		m_velocityIterations = 6;
		m_positionIterations = 2;
	}
	
	public Body addBody(BodyDef bd,FixtureDef fd){	
		Body b = m_theWorld.createBody(bd);
		b.createFixture(fd);
		
		return b;
	}
	
	public void removeBody(Body b){
		if(!m_theWorld.isLocked()){
			m_theWorld.destroyBody(b);
		}
	}
	
	public Body addBody(BodyDef bd,Shape s,float density){
		Body b = m_theWorld.createBody(bd);
		b.createFixture(s, density);
		
		return b;
	}
	
	public void removeJoint(Joint j){
		m_theWorld.destroyJoint(j);
	}
	
	public Joint addJoint(JointDef jd){
		Joint j = m_theWorld.createJoint(jd);
		
		return j;
	}
	
	public void update(){
		m_theWorld.step(m_timeStep, m_velocityIterations, m_velocityIterations);
	}

	public void present(){
//		Matrix4 projectionMatrix = Render.m_batch.getProjectionMatrix();
//		Matrix4 transformMatrix  = Render.m_batch.getTransformMatrix();
//		
//		GL10 gl = Gdx.gl10;
//		
//		gl.glMatrixMode(GL10.GL_PROJECTION);
//		gl.glLoadIdentity();
//		gl.glMultMatrixf(projectionMatrix.val, 0);
//		
//		gl.glMatrixMode(GL10.GL_MODELVIEW);
//		gl.glLoadIdentity();
//		gl.glMultMatrixf(transformMatrix.val, 0);
//		
//		Iterator<Body> i = m_theWorld.getBodies();
//		while(i.hasNext()){
//			Body b = i.next();
//			
//			ArrayList<Fixture> fixtureList = b.getFixtureList();
//			
//			for(Fixture f : fixtureList){
//				if(f.getType() == Type.Polygon){
//					PolygonShape shape = (PolygonShape)f.getShape();
//					
//					Mesh m = new Mesh(true, 4, 4, 
//							new VertexAttribute(VertexAttributes.Usage.Position,3,"a_position"));
//					
//					int c = shape.getVertexCount();
//					
//					float coords[] = new float[c*3];
//					short indices[] = new short[c];
//					
//					for(short e=0;e<c;++e){
//						Vector2 v = new Vector2();
//						shape.getVertex(e , v);
//						
//						indices[e] = e;
//						
//						coords[e*3 + 0] = v.x;
//						coords[e*3 + 1] = v.y;
//						coords[e*3 + 2] = 0;
//					}
//					
//					m.setIndices(indices);
//					m.setVertices(coords);
//					
//					gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
//					
//					Vector2 pos = b.getPosition();
//					float angle = (float)Math.toDegrees(b.getAngle());
//					
//					gl.glPushMatrix();
//					gl.glTranslatef(pos.x, pos.y,0.0f);
//					gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
//					m.render(GL10.GL_LINE_LOOP);
//					m.dispose();
//					gl.glPopMatrix();
//					
//				}
//				if(f.getType() == Type.Circle){
//					CircleShape shape = (CircleShape)f.getShape();
//					
//					Mesh m = new Mesh(true, 32, 32, 
//							new VertexAttribute(VertexAttributes.Usage.Position,3,"a_position"));
//					
//					float vertexes [] = new float[32 * 3]; 
//					short indices  [] = new short[32];
//					
//					Vector2 cpos = shape.getPosition();
//					float radius = shape.getRadius();
//					
//					float angle = (float)Math.PI*2/32;
//					for(short e=0;e<32;++e){
//						vertexes[e*3 + 0] = radius * (float)Math.sin(angle * e) + cpos.x;
//						vertexes[e*3 + 1] = radius * (float)Math.cos(angle * e) + cpos.y;
//						vertexes[e*3 + 2] = 0.0f;
//						
//						indices[e] = e;
//					}
//					m.setVertices(vertexes);
//					m.setIndices(indices);
//					
//					Vector2 pos = b.getPosition();
//					float angle1 = (float)Math.toDegrees(b.getAngle());
//					
//					gl.glColor4f(1.0f, 0.0f , 0.0f, 1.0f);
//					
//					gl.glPushMatrix();
//					gl.glTranslatef(pos.x, pos.y,0.0f);
//					gl.glRotatef(angle1, 0.0f, 0.0f, 1.0f);
//					m.render(GL10.GL_LINE_LOOP);
//					m.dispose();
//					gl.glPopMatrix();
//				}
//		}
	}
	
	public void dispose(){
		m_theWorld.dispose();
	}
	
	@Override
	public void beginContact(Contact arg0) {
		Fixture fa = arg0.getFixtureA();
		Fixture fb = arg0.getFixtureB();
		
		Body ba = fa.getBody();
		Body bb = fb.getBody();
		
		if(ba.getUserData() instanceof Bullet && bb.getUserData() instanceof Monster){
			Bullet b = (Bullet)ba.getUserData();
			Monster m = (Monster)bb.getUserData();
			
			if(b.isCurrentBullet()){
				m.hitted(ba);
			}
			else{
					b.bombed();
					m.hitted(ba);
			}
			
			Resources.playSound(Resources.m_sound_hit);
			Resources.vibrate(10);
			
			return ;
		}
		
		if(bb.getUserData() instanceof Bullet && ba.getUserData() instanceof Monster){
			Bullet b = (Bullet)bb.getUserData();
			Monster m = (Monster)ba.getUserData();
			
			if(b.isCurrentBullet()){
				m.hitted(bb);
			}
			else{
					b.bombed();
					m.hitted(bb);
			}
			
			Resources.playSound(Resources.m_sound_hit);
			Resources.vibrate(10);
			
			return ;
		}
		
		if(ba.getUserData() instanceof Bullet){
			Bullet b = (Bullet)ba.getUserData();
			b.addRef();
			if(b.getRef() > Settings.MAX_BULLET_LIFE){
				b.bombed();
			}
			
			Resources.playSound(Resources.m_sound_hit);
			//Resources.vibrate(10);
		}
		
		if(bb.getUserData() instanceof Bullet){
			Bullet b = (Bullet)bb.getUserData();
			b.addRef();
			if(b.getRef() > Settings.MAX_BULLET_LIFE){
				b.bombed();
			}
			
			Resources.playSound(Resources.m_sound_hit);
			//Resources.vibrate(10);
		}
	}

	@Override
	public void endContact(Contact arg0) {
	}
}
